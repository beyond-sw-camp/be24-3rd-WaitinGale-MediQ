package org.example.mediqback.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String kakaoPlaceId;

    private String name; // 병원명
    private String address; // 주소
    private String phone; // 전화번호

    // 예약금 설정
    private int deposit;

}
