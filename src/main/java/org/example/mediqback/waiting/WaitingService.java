package org.example.mediqback.waiting;

import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.mediqback.notification.NotificationService;
import org.example.mediqback.notification.model.NotificationDto;
import org.example.mediqback.queue.QueueRepository;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.queue.model.Queue;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.jose4j.lang.JoseException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j // 로그 기록을 위해 추가
@Service
public class WaitingService {
    private final WaitingRepository waitingRepository;
    private final QueueService queueService;
    private final QueueRepository queueRepository;
    private final NotificationService notificationService;
    private final PushService pushService;

    public WaitingService(
            WaitingRepository waitingRepository,
            QueueService queueService,
            QueueRepository queueRepository,
            NotificationService notificationService
    ) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        this.waitingRepository = waitingRepository;
        this.queueService = queueService;
        this.queueRepository = queueRepository;
        this.notificationService = notificationService;

        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        this.pushService = new PushService();
        this.pushService.setPublicKey("BEyaCMDqIkd76kJ-WmsXJd2eI2JQEt-Ilx3kzRF-4Sgzu0_2zZkVY3Iesc3DoL5FDZ2MkEGsMhYAA85Q92HvOcw");
        this.pushService.setPrivateKey("jDYqsVMeuG0t7IaKHgR1NpkkuOEHJKuuVDVLSkpcsVA");
        this.pushService.setSubject("mailto:example@yourdomain.com");
    }

    // 대기열 등록하기
    public WaitingDto.WaitingRes register(Long hospitalIdx, Long userIdx) {
        int nextNo = queueService.generateNextWaitingNumber(hospitalIdx);

        WaitingDto.WaitingReq dto = WaitingDto.WaitingReq.builder()
                .hospitalIdx(hospitalIdx)
                .userIdx(userIdx)
                .waitingNumber(nextNo)
                .build();

        Waiting waiting = waitingRepository.save(dto.toEntity(dto));
        return WaitingDto.WaitingRes.from(waiting);
    }

    public int findMyOrder(Long userIdx) {
        Waiting waiting = waitingRepository.findByUserIdx(userIdx);
        return (waiting != null) ? waiting.getWaitingNumber() : -1;
    }

////     현재 대기열 정보 가져오기
//    public List<WaitingDto.ListRes> findListByHospitalId(Long hospitalIdx) {
//        List<Waiting> waitingEntityList = waitingRepository.findAllByHospitalIdx(hospitalIdx);
//        List<WaitingDto.ListRes> listResDtoList = new ArrayList<>();
//        for (Waiting waiting : waitingEntityList) {
//            listResDtoList.add(WaitingDto.ListRes.from(waiting));
//        }
//        return listResDtoList;
//    }



    public Page<WaitingDto.ListRes> findListByHospitalIdx(Long hospitalIdx, int page, int size) {
        // 1. 페이징 및 정렬 조건 설정 (최신순 등 비즈니스에 맞게 설정 가능)
        Pageable pageable = PageRequest.of(page, size, Sort.by("idx").ascending());

        // 2. DB에서 필요한 구간의 데이터만 조회 (성능 향상의 핵심)
        Page<Waiting> waitingPage = waitingRepository.findAllByHospitalIdx(hospitalIdx, pageable);

        // 3. 성능을 위한 for문 루프 및 ArrayList 용량 최적화
        List<Waiting> content = waitingPage.getContent();
        List<WaitingDto.ListRes> listResDtoList = new ArrayList<>(content.size()); // 용량 미리 확보

        for (int i = 0; i < content.size(); i++) {
            listResDtoList.add(WaitingDto.ListRes.from(content.get(i)));
        }

        // 4. Page 구현체로 감싸서 반환 (전체 페이지 수 등의 메타데이터 포함)
        return new PageImpl<>(listResDtoList, pageable, waitingPage.getTotalElements());
    }





    // 대기열 삭제하기
    public WaitingDto.DeleteRes deleteRegistration(Long hospitalIdx, Long userIdx) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(hospitalIdx, userIdx);
        if (waiting != null) {
            waitingRepository.delete(waiting);
            return WaitingDto.DeleteRes.from(waiting);
        }
        return null;
    }

    // 예약 확인
    public WaitingDto.FindRes findWaiting(Long hospitalIdx, Long userIdx, String userName) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(hospitalIdx, userIdx);
        if (waiting != null) {
            WaitingDto.FindRes findRes = WaitingDto.FindRes.from(waiting);
            findRes.setUserName(userName);
            return findRes;
        } else {
            return WaitingDto.FindRes.builder().idx(-1L).build();
        }
    }

    // 대기 번호 업데이트 (한 명 입장 시 호출)
    public List<WaitingDto.findWaitingNumberRes> updateWaitingNumber(Long hospitalIdx, int waitingNumber)
            throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {

        // 1. 대기 번호가 더 큰 사람들 1씩 줄이기
        List<Waiting> waitingList = waitingRepository.findByHospitalIdxAndWaitingNumberGreaterThan(hospitalIdx, waitingNumber);
        List<WaitingDto.findWaitingNumberRes> resultList = new ArrayList<>();

        for (Waiting waiting : waitingList) {
            waiting.decreaseWaitingNumber();
            waitingRepository.save(waiting);
            resultList.add(WaitingDto.findWaitingNumberRes.from(waiting));
        }

        // 2. 전체 대기 인원수(LastNo) 업데이트
        Queue queue = queueService.findQueueByHospitalIdx(hospitalIdx);
        if (queue != null && queue.getLastNo() > 0) {
            queue.updateLastNo(queue.getLastNo() - 1);
            queueRepository.save(queue);
        }

        // 3. 특정 순번에게 알림 전송 (에러가 나도 무시하도록 설계)
        sendNotificationIfPresent(hospitalIdx, 2, "!! 곧 입장입니다 !!", "2번째 순서입니다. 진료를 위해 대기하여 주시기 바랍니다.");
        sendNotificationIfPresent(hospitalIdx, 5, "대기 안내", "현재 5번째 순서입니다. 병원 근처에서 대기해 주세요.");

        return resultList;
    }

    // 알림 전송 공통 메소드
    private void sendNotificationIfPresent(Long hospitalIdx, int number, String title, String message) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndWaitingNumber(hospitalIdx, number);

        if (waiting != null) {
            try {
                // notificationService.send 내부에서 유저 정보를 못 찾아 NoSuchElementException이 발생할 수 있음
                NotificationDto.Send sendDto = NotificationDto.Send.builder()
                        .userIdx(waiting.getUserIdx())
                        .title(title)
                        .message(message)
                        .build();

                notificationService.send(sendDto);
            } catch (Exception e) {
                // 알림 전송 실패가 전체 대기열 로직에 영향을 주지 않도록 예외 처리
                log.error("{}번 순서 유저(userIdx: {})에게 알림 전송 실패: {}", number, waiting.getUserIdx(), e.getMessage());
            }
        }
    }

    // 사람이 예약했는지 확인하는 코드
    public WaitingDto.isReservedRes isReserved(Long userIdx) {
        Waiting waiting = waitingRepository.findByUserIdx(userIdx);
        return WaitingDto.isReservedRes.from(waiting);
    }
}