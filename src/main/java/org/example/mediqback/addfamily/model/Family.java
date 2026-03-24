package org.example.mediqback.addfamily.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.user.model.User;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private int age;
    private String bloodtype;
    private String relation;


    private Long userIdx;

}
