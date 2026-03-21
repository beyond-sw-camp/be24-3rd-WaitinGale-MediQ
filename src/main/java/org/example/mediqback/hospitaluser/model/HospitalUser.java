package org.example.mediqback.hospitaluser.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.mediqback.hospital.model.Hospital;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class HospitalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    private String name; // 담당자 이름

    // 소속 병원 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_idx")
    private Hospital hospital;

    @ColumnDefault(value="'ROLE_HOSPITAL'")
    @Builder.Default
    private String role = "ROLE_HOSPITAL";
}