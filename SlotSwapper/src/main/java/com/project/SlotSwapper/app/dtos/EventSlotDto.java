package com.project.SlotSwapper.app.dtos;

import com.project.SlotSwapper.app.model.EventSlot;
import com.project.SlotSwapper.app.model.EventStatus;
import java.time.Instant;

public record EventSlotDto(
        Long id,
        Long ownerId,
        String title,
        Instant startTime,
        Instant endTime,
        EventStatus status
) {
    public static EventSlotDto from(EventSlot e) {
        return new EventSlotDto(
                e.getId(),
                e.getOwner().getId(),
                e.getTitle(),
                e.getStartTime(),
                e.getEndTime(),
                e.getStatus()
        );
    }
}
