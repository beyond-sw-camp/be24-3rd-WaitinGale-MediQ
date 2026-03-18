package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalDto.SignupRes signup(HospitalDto.SignupReq dto) {
        Hospital hospital = dto.toEntity();
         return HospitalDto.SignupRes.from(hospital);
    }
}
