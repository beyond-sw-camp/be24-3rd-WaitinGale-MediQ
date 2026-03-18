package org.example.mediqback.medicalhistory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.user.model.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MedicalHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    private String hospital;      // 병원명
    private String department;    // 진료과
    private String treatmentDate; // 진료일
    private String doctor;        // 담당의
    private String diagnosis;     // 진단명
    private String prescription;  // 처방내용
}
