package org.example.mediqback.userinfo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String medicalHistory;

    // Entity를 DTO로 변환하는 생성자
    public UserInfoDto(UserInfo userInfo) {
        if (userInfo != null) {
            this.age = userInfo.getAge();
            this.gender = userInfo.getGender();
            this.phoneNumber = userInfo.getPhoneNumber();
            this.medicalHistory = userInfo.getMedicalHistory();
        }
    }
}