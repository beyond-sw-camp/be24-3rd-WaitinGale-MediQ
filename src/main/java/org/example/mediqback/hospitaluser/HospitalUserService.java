package org.example.mediqback.hospitaluser;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.HospitalRepository;
import org.example.mediqback.hospital.model.Hospital;
import org.example.mediqback.hospitaluser.model.HospitalUser;
import org.example.mediqback.hospitaluser.model.HospitalUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalUserService {

    private final HospitalUserRepository hospitalUserRepository;
    private final HospitalRepository hospitalRepository; // 건물 정보를 찾기 위해 필요
    private final PasswordEncoder passwordEncoder;

    public HospitalUserDto.Res signup(HospitalUserDto.SignupReq dto) {
        // 이메일 중복 체크 (사람 테이블)
        if (hospitalUserRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입된 병원 관계자 이메일입니다.");
        }

        // 병원 존재 여부 확인 (건물 테이블 - 카카오 ID로 조회)
        Hospital hospital = hospitalRepository.findByKakaoPlaceId(dto.getKakaoPlaceId())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 병원 ID입니다. 먼저 병원을 등록해주세요."));

        // 사람(HospitalUser) 객체 생성 시 위에서 찾은 'hospital' 연결
        HospitalUser hospitalUser = dto.toEntity(hospital);

        // 비밀번호 암호화 후 저장
        hospitalUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        HospitalUser savedUser = hospitalUserRepository.save(hospitalUser);

        return HospitalUserDto.Res.from(savedUser);
    }

    @Transactional(readOnly = true)
    public HospitalUserDto.Res login(HospitalUserDto.LoginReq dto) {
        HospitalUser user = hospitalUserRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return HospitalUserDto.Res.from(user);
    }
}