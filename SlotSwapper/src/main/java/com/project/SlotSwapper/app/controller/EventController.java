package com.project.SlotSwapper.app.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.SlotSwapper.app.dtos.EventSlotDto;
import com.project.SlotSwapper.app.model.EventSlot;
import com.project.SlotSwapper.app.model.EventStatus;
import com.project.SlotSwapper.app.service.EventService;
import com.project.SlotSwapper.app.util.CurrentUser;

import java.time.Instant;
import java.util.List;

@CrossOrigin(
    origins = "http://localhost:4200",  
    allowedHeaders = "*",
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS },
    allowCredentials = "true",
    maxAge = 3600
)
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService svc;

    @GetMapping
    public List<EventSlotDto> myEvents() {
        return svc.myEvents(CurrentUser.id())
                .stream()
                .map(EventSlotDto::from)
                .toList();
    }

    @PostMapping
    public EventSlot create(@RequestBody CreateEventDto dto){
        return svc.create(CurrentUser.id(), dto.title, dto.startTime, dto.endTime, dto.status);
    }

    @PutMapping("/{id}")
    public EventSlot update(@PathVariable Long id, @RequestBody UpdateEventDto dto){
        return svc.update(CurrentUser.id(), id, dto.title, dto.startTime, dto.endTime, dto.status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        svc.delete(CurrentUser.id(), id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping("/swappable")
    public List<EventSlot> swappable(){
        return svc.marketplace(CurrentUser.id());
    }

    @Data
    public static class CreateEventDto{
        @NotBlank public String title;
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) public Instant startTime;
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) public Instant endTime;
        @NotNull public EventStatus status;
    }
    @Data
    public static class UpdateEventDto{
        public String title; 
        public Instant startTime; 
        public Instant endTime; 
        public EventStatus status;
    }
}