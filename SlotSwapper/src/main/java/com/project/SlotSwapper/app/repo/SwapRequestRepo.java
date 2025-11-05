package com.project.SlotSwapper.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SlotSwapper.app.model.SwapRequest;

public interface SwapRequestRepo extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByResponderIdOrderByCreatedAtDesc(Long userId); 
    List<SwapRequest> findByRequesterIdOrderByCreatedAtDesc(Long userId); 
}