package org.example.mediqback.waiting;

import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.mediqback.notification.NotificationRepository;
import org.example.mediqback.notification.NotificationService;
import org.example.mediqback.notification.model.NotificationDto;
import org.example.mediqback.notification.model.NotificationEntity;
import org.example.mediqback.queue.QueueRepository;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.queue.model.Queue;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.jose4j.lang.JoseException;
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

        // BouncyCastle 및 PushService 설정
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        this.pushService = new PushService();
        this.pushService.setPublicKey("BEyaCMDqIkd76kJ-WmsXJd2eI2JQEt-Ilx3kzRF-4Sgzu0_2zZkVY3Iesc3DoL5FDZ2MkEGsMhYAA85Q92HvOcw");
        this.pushService.setPrivateKey("jDYqsVMeuG0t7IaKHgR1NpkkuOEHJKuuVDVLSkpcsVA");
        this.pushService.setSubject("mailto:example@yourdomain.com"); // Subject는 보통 mailto: 형식을 권장합니다.
    }

    // 대기열 등록하기
    public WaitingDto.WaitingRes register(/*WaitingDto.WaitingReq dto*/ Long hospitalIdx, Long userIdx) {
        // 일단 병원만 프론트에서 검색해서 전달받기로
//         전달받던 매개변수
//        Waiting entity = waitingRepository.save(dto.toEntity(dto));
//        return WaitingDto.WaitingRes.from(entity);

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
        if (waiting != null) {
            return waiting.getWaitingNumber();
        } else {
            return -1;
        }
    }

    // 현재 대기열 정보 가져오기
    public List<WaitingDto.ListRes> findListByHospitalId(Long hospitalIdx) {
        List<Waiting> waitingEntityList = waitingRepository.findAllByHospitalIdx(hospitalIdx);
        List<WaitingDto.ListRes> listResDtoList = new ArrayList<>();
        for (Waiting waiting : waitingEntityList) {
            listResDtoList.add(WaitingDto.ListRes.from(waiting));
        }
        return listResDtoList;
    }




    // 대기열 삭제하기
    public WaitingDto.DeleteRes deleteRegistration (Long hospitalIdx, Long userIdx) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(hospitalIdx, userIdx);
        if (waiting != null) {
            waitingRepository.delete(waiting);
            return WaitingDto.DeleteRes.from(waiting);
        }
        return null;
    }


    // 병원 idx와 로그인 한 사용자 idx로 병원 예약 대기열에 있는지 확인
    public WaitingDto.FindRes findWaiting(Long hospitalIdx, Long userIdx, String userName) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(hospitalIdx, userIdx);

        // 조회 결과가 있다면
        if (waiting != null) {
            WaitingDto.FindRes findRes = WaitingDto.FindRes.from(waiting);
            findRes.setUserName(userName);
            return findRes;
        } else {
            // 조회 결과가 없다면
            WaitingDto.FindRes findRes = WaitingDto.FindRes.builder()
                                                    .idx(-1L)
                                                    .build();
            return findRes;
        }
    }


    // 병원 번호와 waiting 번호 입력받고 대기열 번호 큰 사람 1씩 빼주기
//    public List<WaitingDto.findWaitingNumberRes> updateWaitingNumber(Long hospitalIdx, int waitingNumber) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
//
//        List<Waiting> waitingList = waitingRepository.findByHospitalIdxAndWaitingNumberGreaterThan(hospitalIdx, waitingNumber);
//        List<WaitingDto.findWaitingNumberRes> resultList = new ArrayList<>();
//        for (Waiting waiting : waitingList) {
//            waiting.decreaseWaitingNumber();
//            waitingRepository.save(waiting);
//            resultList.add(WaitingDto.findWaitingNumberRes.from(waiting));
//        }
//
//        // 대기열에 한 명 빠졌으면 대기하고 있는 총 인원수 - 1 해주기
//        Queue queue = queueService.findQueueByHospitalIdx(hospitalIdx);
//        queue.updateLastNo(queue.getLastNo() - 1);
//        queueRepository.save(queue);
//
//
//        // 2번째 5번째 된 사람에게 메세지 보내기
//        Waiting secondWaiting = waitingRepository.findByHospitalIdxAndWaitingNumber(hospitalIdx, 2);
//        Waiting fifthWaiting = waitingRepository.findByHospitalIdxAndWaitingNumber(hospitalIdx, 5);
//
//        // 2번째인사람
//        NotificationDto.SearchRes secondNotification = notificationService.findByUserIdx(secondWaiting.getUserIdx());
//        NotificationDto.Send secondDto = NotificationDto.Send.builder()
//                .userIdx(secondNotification.getUserIdx())
//                .title("!! 곧 입장입니다 !!")
//                .message("2번째 순서입니다. 진료를 위해 편한 복장으로 대기하여 주시기 바랍니다.")
//                .build();
//        notificationService.send(secondDto);
//        // 5번재인사람
//        NotificationDto.SearchRes fifthNotification = notificationService.findByUserIdx(fifthWaiting.getUserIdx());
//
//
//        // 두번째인 사람한테는 곧 입장하라고 알림 보내기
//
//
//
//
//        // 5번째인 사람은 근처에서 대기하라는 비슷한 뉘앙스의 알림 보내기
//
//
//
//
//
//
//        return resultList;
//    }




    // 번호 업데이트 및 알림 전송
    public List<WaitingDto.findWaitingNumberRes> updateWaitingNumber(Long hospitalIdx, int waitingNumber)
            throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {

        List<Waiting> waitingList = waitingRepository.findByHospitalIdxAndWaitingNumberGreaterThan(hospitalIdx, waitingNumber);
        List<WaitingDto.findWaitingNumberRes> resultList = new ArrayList<>();

        for (Waiting waiting : waitingList) {
            waiting.decreaseWaitingNumber();
            waitingRepository.save(waiting);
            resultList.add(WaitingDto.findWaitingNumberRes.from(waiting));
        }

        Queue queue = queueService.findQueueByHospitalIdx(hospitalIdx);
        if (queue != null && queue.getLastNo() > 0) {
            queue.updateLastNo(queue.getLastNo() - 1);
            queueRepository.save(queue);
        }

        sendNotificationIfPresent(hospitalIdx, 2, "!! 곧 입장입니다 !!", "2번째 순서입니다. 진료를 위해 대기하여 주시기 바랍니다.");
        sendNotificationIfPresent(hospitalIdx, 5, "대기 안내", "현재 5번째 순서입니다. 병원 근처에서 대기해 주세요.");

        return resultList;
    }

    private void sendNotificationIfPresent(Long hospitalIdx, int number, String title, String message) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {

        Waiting waiting = waitingRepository.findByHospitalIdxAndWaitingNumber(hospitalIdx, number);

        if (waiting != null) {
            NotificationDto.Send sendDto = NotificationDto.Send.builder()
                    .userIdx(waiting.getUserIdx())
                    .title(title)
                    .message(message)
                    .build();


            notificationService.send(sendDto);
        }
    }
}
