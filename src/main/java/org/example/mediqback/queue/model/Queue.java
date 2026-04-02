package org.example.mediqback.queue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCurrentNo(int newCurrentNo) {
        this.currentNo = newCurrentNo;
        this.updatedAt = LocalDateTime.now(); // 변경 시 시간도 함께 갱신
    }
}
