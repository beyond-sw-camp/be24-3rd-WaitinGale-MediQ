package org.example.mediqback.hospital.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true)
    private String placeId;

    private String email;
    private String name;
    private String location;
    private String tell;

    @Setter
    private String password;

    @Setter
    private boolean enable;

}
