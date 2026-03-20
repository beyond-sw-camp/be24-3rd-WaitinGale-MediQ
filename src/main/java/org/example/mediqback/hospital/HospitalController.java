package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals") // 병원 관련 API는 이 주소로 시작하도록 설정
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("/check")
    public ResponseEntity<BaseResponse<HospitalDto.Res>> checkAndSaveHospital(@RequestBody HospitalDto.Req reqDto) {

        // 서비스를 호출해서 로직을 실행하고 결과를 받음
        HospitalDto.Res response = hospitalService.checkAndSaveHospital(reqDto);

        // BaseResponse 성공 규격에 맞춰서 프론트엔드로 응답
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}