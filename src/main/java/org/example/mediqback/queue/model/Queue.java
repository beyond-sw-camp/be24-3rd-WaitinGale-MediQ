package org.example.mediqback.queue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long hospitalIdx;
    private int currentNo;
    private int lastNo;

    private LocalDateTime updatedAt;

    public void updateLastNo(int newLastNo) {
        this.lastNo = newLastNo;
    }
}
