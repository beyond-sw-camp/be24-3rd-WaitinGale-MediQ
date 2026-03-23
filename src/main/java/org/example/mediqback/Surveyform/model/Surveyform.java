package org.example.mediqback.Surveyform.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.user.model.User;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Surveyform extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> symptoms;
    private String otherSymptom;
    private LocalDate symptomStartDate;

    private Boolean takingMedicine;
    private String medicineDetail;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> diseases;

    private Boolean allergy;
    private String allergyDetail;
    private Boolean travel;
}