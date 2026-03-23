package org.example.mediqback.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String email;
    private String name;

    @Setter
    private String password;

    @Setter
    private boolean enable;

    @ColumnDefault(value="'ROLE_USER'")
    private String role;
    private String provider; // "KAKAO", "GOOGLE", "LOCAL" 등
    private String socialId; // 카카오에서 넘어오는 고유 ID값
}