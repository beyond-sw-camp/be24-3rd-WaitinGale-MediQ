package org.example.mediqback.userinfo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "개인 의료 정보 DTO")
@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    @Schema(description = "나이", example = "30")
    private Integer age;
    @Schema(description = "성별", example = "MALE")
    private String gender;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;
    @Schema(description = "기저질환 및 알러지 (자유 기재)", example = "고혈압, 꽃가루 알레르기")
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