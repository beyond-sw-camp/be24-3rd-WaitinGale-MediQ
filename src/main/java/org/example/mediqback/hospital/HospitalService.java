package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class  HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalDto.SignupRes signup(HospitalDto.SignupReq dto) {
        Hospital hospital = dto.toEntity();
         return HospitalDto.SignupRes.from(hospital);
    }

    public HospitalDto.LoginRes login(HospitalDto.LoginReq dto) {
        return null;
    }

    // 목록 조회 로직 추가
    public List<HospitalDto.SearchRes> getRegisteredHospitals() {
        return hospitalRepository.findAll().stream()
                .map(HospitalDto.SearchRes::from)
                .collect(Collectors.toList());
    }
}
