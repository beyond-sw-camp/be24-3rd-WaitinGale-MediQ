package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    @Transactional
    public HospitalDto.Res checkAndSaveHospital(HospitalDto.Req reqDto) {

        // 카카오맵 ID로 DB에서 병원 조회
        Optional<Hospital> optionalHospital = hospitalRepository.findByKakaoPlaceId(reqDto.getKakaoPlaceId());

        Hospital hospital;
        if (optionalHospital.isPresent()) {
            // 이미 우리 DB에 등록된 병원이면 정보를 가져옴
            hospital = optionalHospital.get();
        } else {
            // 새로운 병원이면 새로 DB에 저장
            hospital = hospitalRepository.save(reqDto.toEntity());
        }

        // 프론트엔드에 돌려줄 응답용 리턴
        return HospitalDto.Res.from(hospital);
    }

    // 목록 조회 로직 추가
    public List<HospitalDto.SearchRes> getRegisteredHospitals() {
        return hospitalRepository.findAll().stream()
                .map(HospitalDto.SearchRes::from)
                .collect(Collectors.toList());
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }
}