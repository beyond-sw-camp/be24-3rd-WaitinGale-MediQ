package org.example.mediqback.paymenthistory;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.paymenthistory.model.PaymentHistory;
import org.example.mediqback.paymenthistory.model.PaymentHistoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    @Transactional(readOnly = true)
    public List<PaymentHistoryDto.Response> getMyPaymentHistories(Long userIdx) {

        List<PaymentHistory> historyList = paymentHistoryRepository.findAllByUserIdxOrderByCreatedAtDesc(userIdx);

        return historyList.stream()
                .map(PaymentHistoryDto.Response::from)
                .collect(Collectors.toList());
    }
}