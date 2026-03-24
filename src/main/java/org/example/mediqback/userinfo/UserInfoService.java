package org.example.mediqback.userinfo;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.user.UserRepository;
import org.example.mediqback.user.model.User;
import org.example.mediqback.userinfo.model.UserInfo;
import org.example.mediqback.userinfo.model.UserInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    // 정보 조회
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(String email) {
        UserInfo userInfo = userInfoRepository.findByUser_Email(email).orElse(null);
        return new UserInfoDto(userInfo);
    }

    // 정보 저장 및 수정
    @Transactional
    public void updateUserInfo(String email, UserInfoDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        UserInfo userInfo = userInfoRepository.findByUser_Email(email)
                .orElseGet(() -> {
                    UserInfo newUserInfo = new UserInfo();
                    newUserInfo.setUser(user);
                    return newUserInfo;
                });

        // 컬럼 업데이트
        userInfo.setAge(requestDto.getAge());
        userInfo.setGender(requestDto.getGender());
        userInfo.setPhoneNumber(requestDto.getPhoneNumber());
        userInfo.setMedicalHistory(requestDto.getMedicalHistory());
        userInfoRepository.save(userInfo);
    }
}