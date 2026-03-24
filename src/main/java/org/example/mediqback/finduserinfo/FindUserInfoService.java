package org.example.mediqback.finduserinfo;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.finduserinfo.model.FindUserInfoDto;
import org.example.mediqback.user.EmailService;
import org.example.mediqback.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserInfoService {

    private final FindUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 1. 아이디(이메일) 찾기 (오직 이름으로만)
    @Transactional(readOnly = true)
    public String findEmail(FindUserInfoDto.FindIdReq req) {

        // 이름으로 유저 목록 조회
        List<User> users = userRepository.findByName(req.getName());

        if (users.isEmpty()) {
            throw new IllegalArgumentException("입력하신 이름으로 가입된 계정이 없습니다.");
        }

        // 우선 첫 번째 유저의 이메일을 마스킹 처리하여 반환
        return maskEmail(users.get(0).getEmail());
    }

    // 2. 임시 비밀번호 발급 및 메일 전송
    @Transactional
    public void sendTempPassword(FindUserInfoDto.FindPwReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (user.getProvider() != null && !user.getProvider().equals("LOCAL")) {
            throw new IllegalArgumentException("카카오 등 소셜 로그인 회원은 비밀번호 찾기를 이용할 수 없습니다.");
        }

        String tempPassword = "Med" + UUID.randomUUID().toString().substring(0, 6) + "!";
        user.setPassword(passwordEncoder.encode(tempPassword));

        emailService.sendTempPassword(user.getEmail(), tempPassword);
    }

    // 이메일 마스킹 처리
    private String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex > 0) {
            String id = email.substring(0, atIndex);
            String domain = email.substring(atIndex);
            if (id.length() <= 3) {
                return id.substring(0, 1) + "***" + domain;
            } else {
                return id.substring(0, 3) + "***" + domain;
            }
        }
        return email;
    }
}