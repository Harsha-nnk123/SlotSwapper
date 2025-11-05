package com.project.SlotSwapper.app.repo;

import com.project.SlotSwapper.app.model.EventSlot;
import com.project.SlotSwapper.app.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<EventSlot, Long> {

    List<EventSlot> findByOwner_Id(Long ownerId);
    Optional<EventSlot> findByIdAndOwner_Id(Long id, Long ownerId);
    List<EventSlot> findByStatusAndOwner_IdNot(EventStatus status, Long ownerId);
}
