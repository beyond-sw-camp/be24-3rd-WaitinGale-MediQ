package org.example.mediqback.hospital;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hospital API", description = "병원 등록 및 조회 API")
@RestController
@RequestMapping("/api/hospitals") // 병원 관련 API는 이 주소로 시작하도록 설정
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @Operation(summary = "병원 정보 확인 및 저장", description = "카카오맵 ID로 병원을 확인하고, 없으면 DB에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "병원 확인/저장 성공")
    })
    @PostMapping("/check")
    public ResponseEntity<BaseResponse<HospitalDto.Res>> checkAndSaveHospital(@RequestBody HospitalDto.Req reqDto) {

        // 서비스를 호출해서 로직을 실행하고 결과를 받음
        HospitalDto.Res response = hospitalService.checkAndSaveHospital(reqDto);

        // BaseResponse 성공 규격에 맞춰서 프론트엔드로 응답
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "등록된 병원 목록 조회", description = "DB에 등록된 모든 병원 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "목록 조회 성공")
    })
    @GetMapping("/list")
    public ResponseEntity<List<HospitalDto.SearchRes>> getHospitalList() {
        List<HospitalDto.SearchRes> list = hospitalService.getRegisteredHospitals();
        return ResponseEntity.ok(list);
    }
}