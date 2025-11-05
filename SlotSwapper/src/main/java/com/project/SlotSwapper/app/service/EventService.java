package com.project.SlotSwapper.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.SlotSwapper.app.model.EventSlot;
import com.project.SlotSwapper.app.model.EventStatus;
import com.project.SlotSwapper.app.repo.EventRepo;
import com.project.SlotSwapper.app.repo.UserRepo;
import com.project.SlotSwapper.app.util.TimeUtils;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepo events;
    private final UserRepo users;

    public List<EventSlot> myEvents(Long userId){
        return events.findByOwner_Id(userId);
    }

    @Transactional
    public EventSlot create(Long userId, String title, Instant start, Instant end, EventStatus status){
        TimeUtils.requireStartBeforeEnd(start, end);
        var owner = users.findById(userId).orElseThrow();
        var e = EventSlot.builder().owner(owner).title(title).startTime(start).endTime(end).status(status).build();
        return events.save(e);
    }

    @Transactional
    public EventSlot update(Long userId, Long eventId, String title, Instant start, Instant end, EventStatus status){
        var e = events.findById(eventId).orElseThrow();
        if(!e.getOwner().getId().equals(userId)) throw new RuntimeException("Forbidden");
        if(start != null && end != null) TimeUtils.requireStartBeforeEnd(start, end);
        if(title != null) e.setTitle(title);
        if(start != null) e.setStartTime(start);
        if(end != null) e.setEndTime(end);
        if(status != null) e.setStatus(status);
        return e;
    }

    @Transactional
    public void delete(Long userId, Long eventId){
        var e = events.findById(eventId).orElseThrow();
        if(!e.getOwner().getId().equals(userId)) throw new RuntimeException("Forbidden");
        events.delete(e);
    }

    public List<EventSlot> marketplace(Long userId){
        return events.findByStatusAndOwner_IdNot(EventStatus.SWAPPABLE, userId);
    }
}
