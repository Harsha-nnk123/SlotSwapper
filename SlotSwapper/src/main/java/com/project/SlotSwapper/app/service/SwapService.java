package com.project.SlotSwapper.app.service;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.SlotSwapper.app.model.EventStatus;
import com.project.SlotSwapper.app.model.SwapRequest;
import com.project.SlotSwapper.app.model.SwapStatus;
import com.project.SlotSwapper.app.repo.EventRepo;
import com.project.SlotSwapper.app.repo.SwapRequestRepo;
import com.project.SlotSwapper.app.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SwapService {
    private final EventRepo events;
    private final UserRepo users;
    private final SwapRequestRepo swaps;

    public record SwapRequestPayload(Long mySlotId, Long theirSlotId) {}

    @Transactional
    public SwapRequest createRequest(Long requesterId, Long mySlotId, Long theirSlotId){
        var requester = users.findById(requesterId).orElseThrow();
        var mySlot = events.findById(mySlotId).orElseThrow();
        var theirSlot = events.findById(theirSlotId).orElseThrow();


        if(!mySlot.getOwner().getId().equals(requesterId)) throw new RuntimeException("You don't own the offered slot");
        if(mySlot.getStatus() != EventStatus.SWAPPABLE || theirSlot.getStatus() != EventStatus.SWAPPABLE)
        throw new RuntimeException("Both slots must be SWAPPABLE");
        if(theirSlot.getOwner().getId().equals(requesterId)) throw new RuntimeException("Cannot request your own slot");

        mySlot.setStatus(EventStatus.SWAP_PENDING);
        theirSlot.setStatus(EventStatus.SWAP_PENDING);


        var req = SwapRequest.builder()
        .requester(requester)
        .responder(theirSlot.getOwner())
        .mySlot(mySlot)
        .theirSlot(theirSlot)
        .status(SwapStatus.PENDING)
        .createdAt(Instant.now())
        .build();
        return swaps.save(req);
    }

    public List<SwapRequest> incoming(Long userId){ 
        return swaps.findByResponderIdOrderByCreatedAtDesc(userId); 
    }

    public List<SwapRequest> outgoing(Long userId){ 
        return swaps.findByRequesterIdOrderByCreatedAtDesc(userId); 
    }

    @Transactional
    public SwapRequest respond(Long responderId, Long requestId, boolean accept){
        var req = swaps.findById(requestId).orElseThrow();
        if(!req.getResponder().getId().equals(responderId)) throw new RuntimeException("Forbidden");

        var mySlot = req.getMySlot();
        var theirSlot = req.getTheirSlot();

        if(req.getStatus() != SwapStatus.PENDING) throw new RuntimeException("Request not pending");

        if(!mySlot.getStatus().equals(EventStatus.SWAP_PENDING) || !theirSlot.getStatus().equals(EventStatus.SWAP_PENDING))
            throw new RuntimeException("Slots not pending anymore");

        if(!accept){
            req.setStatus(SwapStatus.REJECTED);
            mySlot.setStatus(EventStatus.SWAPPABLE);
            theirSlot.setStatus(EventStatus.SWAPPABLE);
            return req;
        }

        var ownerA = mySlot.getOwner();
        var ownerB = theirSlot.getOwner();

        mySlot.setOwner(ownerB);
        theirSlot.setOwner(ownerA);
        mySlot.setStatus(EventStatus.BUSY);
        theirSlot.setStatus(EventStatus.BUSY);

        req.setStatus(SwapStatus.ACCEPTED);
        return req;
    }
}