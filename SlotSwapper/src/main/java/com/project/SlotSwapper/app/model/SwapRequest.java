package com.project.SlotSwapper.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity @Table(name = "swap_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SwapRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User requester;
    @ManyToOne(optional = false)
    private User responder;
    @ManyToOne(optional = false)
    private EventSlot mySlot; 
    @ManyToOne(optional = false)
    private EventSlot theirSlot; 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwapStatus status; 
    @Column(nullable = false)
    private Instant createdAt;
}