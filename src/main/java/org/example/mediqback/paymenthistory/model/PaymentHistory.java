package org.example.mediqback.paymenthistory.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.user.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class PaymentHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    // 결제 항목 (진료비, 예약금 등등)
    private String paymentName;

    // 결제  금액
    private Long amount;

    // 결제수단 (앱결제, 병원 현장결제, 예약)
    private String paymentMethod;

    // 결제 상태 (결재완료, 결제취소)
    private String paymentStatus;

    // 예약 정보와 연결하기 위한 식별자
    private Long reservationId;


}
