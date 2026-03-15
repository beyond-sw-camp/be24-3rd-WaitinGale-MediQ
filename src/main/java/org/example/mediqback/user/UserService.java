package org.example.mediqback.user;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.exception.BaseException;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.model.EmailVerify;
import org.example.mediqback.user.model.User;
import org.example.mediqback.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.example.mediqback.common.model.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService { // 시큐리티 인터페이스
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 도구
    private final EmailService emailService;
    private final EmailVerifyRepository emailVerifyRepository;

    public UserDto.SignupRes signup(UserDto.SignupReq dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw BaseException.from(SIGNUP_DUPLICATE_EMAIL);
        }

        User user = dto.toEntity();
        // 비밀번호 암호화해서 저장
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        // 이메일 전송
        String uuid = UUID.randomUUID().toString();
        //emailService.sendWelcomeMail(uuid, dto.getEmail());

        EmailVerify emailVerify = EmailVerify.builder().email(dto.getEmail()).uuid(uuid).build();
        emailVerifyRepository.save(emailVerify);

        return UserDto.SignupRes.from(user);
    }

    // 시큐리티가 로그인할 때 DB에서 회원을 찾는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> BaseException.from(LOGIN_INVALID_USERINFO)
        );
        return AuthUserDetails.from(user);
    }

    public void verify(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElseThrow(
                () -> BaseException.from(SIGNUP_INVALID_UUID)
        );
        User user = userRepository.findByEmail(emailVerify.getEmail()).orElseThrow();
        user.setEnable(true);
        userRepository.save(user);
    }

    public User kakaoLogin(String socialId, String nickname, String email) {
        // 1. 기존에 가입한 카카오 회원인지 DB에서 조회
        return userRepository.findBySocialIdAndProvider(socialId, "KAKAO")
                .orElseGet(() -> {
                    // 2. 처음 온 회원이면 강제로 회원가입을 진행합니다.

                    // 카카오는 이메일 제공이 필수가 아닐 수 있어서, 이메일이 없으면 임의로 만들어줍니다.
                    String defaultEmail = (email != null && !email.isEmpty()) ? email : socialId + "@kakao.com";

                    User newUser = User.builder()
                            .email(defaultEmail)
                            // 소셜 로그인 회원은 비밀번호를 안 쓰므로, 겹치지 않게 무작위 UUID로 암호화해서 넣습니다.
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .name(nickname)
                            .role("ROLE_USER")
                            .enable(true) // 이메일 인증 과정을 거칠 필요 없으므로 바로 활성화(true)
                            .provider("KAKAO")
                            .socialId(socialId)
                            .build();

                    // DB에 저장하고 리턴
                    return userRepository.save(newUser);
                });
    }
}