package org.example.mediqback.orders.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.user.model.User;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders extends BaseEntity { // 생성일자 기록용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 누가 결제했느지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    // 어느 병원에 결제했는지?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_idx")
    private Hospital hospital;

    @ColumnDefault("false")
    private boolean paid; // 결제 완료 여부

    private int paymentPrice; // 실제 결제할 예약금 금액

    private String pgPaymentId; // 포트원 등 PG사 고유 결제 ID

    private String reservationDate; // 방문 날짜 (예: 2026-03-25)
    private String reservationTime; // 방문 시간 (예: 14:30)
}