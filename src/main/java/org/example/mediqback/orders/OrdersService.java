package org.example.mediqback.orders;

import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.nimbusds.jose.shaded.gson.ToNumberPolicy;
import io.portone.sdk.server.payment.PaidPayment;
import io.portone.sdk.server.payment.Payment;
import io.portone.sdk.server.payment.PaymentClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mediqback.hospital.HospitalRepository;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.orders.model.Orders;
import org.example.mediqback.orders.model.OrdersDto;
import org.example.mediqback.paymenthistory.PaymentHistoryRepository;
import org.example.mediqback.paymenthistory.model.PaymentHistory;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final OrdersRepository ordersRepository;
    private final HospitalRepository hospitalRepository;
    private final PaymentClient pg;

    // 프론트엔드에서 결제창 띄우기 직전에 "결제 대기" 주문서 만들기
    @Transactional
    public OrdersDto.OrdersRes create(AuthUserDetails user, OrdersDto.OrdersReq dto) {

        // 예약하려는 병원 찾기
        Hospital hospital = hospitalRepository.findById(dto.getHospitalIdx())
                .orElseThrow(() -> new RuntimeException("병원을 찾을 수 없습니다."));

        // 장바구니 아이템 다 지우고, 바로 Orders 하나만 생성
        Orders orders = Orders.builder()
                .user(user.toEntity())
                .hospital(hospital)
//                .paymentPrice(hospital.getDeposit()) // 병원 DB에 설정된 예약금을 그대로 사용
                .paymentPrice(1)
                .paid(false)
                .reservationDate(dto.getReservationDate())
                .reservationTime(dto.getReservationTime())
                .build();

        Orders savedOrders = ordersRepository.save(orders);

        return OrdersDto.OrdersRes.from(savedOrders);
    }

    // 포트원 결제 완료 후 금액 검증
    @Transactional
    public void verify(AuthUserDetails user, OrdersDto.VerifyReq dto) {
        CompletableFuture<Payment> completableFuture = pg.getPayment(dto.getPaymentId());
        Payment payment = completableFuture.join();

        if (payment instanceof PaidPayment paidPayment) {
            Map<String, Object> customData = new GsonBuilder()
                    .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                    .create().fromJson(paidPayment.getCustomData(), Map.class);

            Long ordersIdx = Long.parseLong(customData.get("ordersIdx").toString());
            Orders orders = ordersRepository.findById(ordersIdx)
                    .orElseThrow(() -> new RuntimeException("주문 정보를 찾을 수 없습니다."));

            // 우리 DB에 저장된 원래 내야 할 예약금 금액
            int totalPrice = orders.getPaymentPrice();

            // 유저가 실제로 결제한 금액과 우리 DB의 금액이 일치하면 결제 완료 처리!
            if (paidPayment.getAmount().getTotal() == totalPrice) {
                orders.setPaid(true);
                orders.setPgPaymentId(dto.getPaymentId());
                ordersRepository.save(orders);

                PaymentHistory paymentHistory = PaymentHistory.builder()
                        .user(orders.getUser()) // 결제한 유저
                        .paymentName(orders.getHospital().getName() + " 예약금")
                        .amount((long) totalPrice) // 결제한 금액
                        .paymentMethod("앱결제") // 결제 수단
                        .paymentStatus("결제완료") // 현재 상태
                        .reservationId(orders.getIdx()) // 어떤 예약에 대한 결제인지 연결고리 (주문번호 저장)
                        .build();

                paymentHistoryRepository.save(paymentHistory); // DB에 저장

            }
        }
    }

    // 마이페이지에 보여줄 내 예약 일정 가져오기
    public List<Map<String, Object>> getMySchedule(AuthUserDetails user) {
        List<Orders> myOrders = ordersRepository.findAll().stream()
                .filter(order -> order.getUser().getIdx().equals(user.getIdx()) && order.isPaid())
                .toList();

        List<Map<String, Object>> scheduleList = new ArrayList<>();
        for (Orders order : myOrders) {
            Map<String, Object> map = new HashMap<>();

            String date = order.getReservationDate();
            String time = order.getReservationTime();

            // 날짜 데이터가 있으면 월/일 분리해서 예쁘게 세팅
            if (date != null && date.contains("-")) {
                String[] dateParts = date.split("-");
                map.put("month", Integer.parseInt(dateParts[1]) + "월");
                map.put("day", dateParts[2]);
            } else {
                map.put("month", "예약");
                map.put("day", "확정");
            }

            map.put("hospital", order.getHospital().getName());

            // 설명칸에 시간 표시
            String timeStr = (time != null && !time.isEmpty()) ? time : "시간 미정";
            map.put("description", "방문 예정 · " + timeStr);
            map.put("bgClass", "bg-indigo-50 text-indigo-600");

            scheduleList.add(map);
        }
        return scheduleList;
    }
}