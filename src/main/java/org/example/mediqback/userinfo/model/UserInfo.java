package org.example.mediqback.userinfo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.mediqback.user.model.User;

@Entity
@Getter @Setter
public class UserInfo {
    @Id
    private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer age;          // 나이
    private String gender;        // 성별 (예: "MALE", "FEMALE" 등)
    private String phoneNumber;   // 전화번호

    @Column(columnDefinition = "TEXT")
    private String medicalHistory; // 기저질환 및 알러지
}
