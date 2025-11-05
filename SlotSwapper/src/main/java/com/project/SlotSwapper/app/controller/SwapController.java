package com.project.SlotSwapper.app.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.SlotSwapper.app.model.SwapRequest;
import com.project.SlotSwapper.app.service.SwapService;
import com.project.SlotSwapper.app.util.CurrentUser;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SwapController {
    private final SwapService svc;

    @GetMapping("/swappable-slots")
    public ResponseEntity<?> swappableSlots(){
        return ResponseEntity.status(308).header("Location","/api/events/swappable").build();
    }

    @PostMapping("/swap-request")
    public SwapRequest create(@RequestBody CreateSwapDto dto){
        return svc.createRequest(CurrentUser.id(), dto.mySlotId, dto.theirSlotId);
    }

    @GetMapping("/requests/incoming")
    public List<SwapRequest> incoming(){ 
        return svc.incoming(CurrentUser.id()); 
    }

    @GetMapping("/requests/outgoing")
    public List<SwapRequest> outgoing(){ 
        return svc.outgoing(CurrentUser.id()); 
    }

    @PostMapping("/swap-response/{id}")
    public SwapRequest respond(@PathVariable Long id, @RequestBody RespondDto dto){
        return svc.respond(CurrentUser.id(), id, dto.accept);
    }

    @Data
    public static class CreateSwapDto { 
        @NotNull public Long mySlotId; @NotNull public Long theirSlotId; 
    }

    @Data
    public static class RespondDto { 
        public boolean accept; 
    }
}