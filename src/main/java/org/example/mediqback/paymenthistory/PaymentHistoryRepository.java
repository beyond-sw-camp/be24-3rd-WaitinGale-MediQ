package org.example.mediqback.paymenthistory;

import org.example.mediqback.paymenthistory.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    // 유저 번호로 조회 - 생성, 결제일자 기준 내림차순 정렬
    List<PaymentHistory> findAllByUserIdxOrderByCreatedAtDesc(Long userIdx);
}