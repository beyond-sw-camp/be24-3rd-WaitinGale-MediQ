package org.example.mediqback.hospital;

import org.example.mediqback.hospital.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByLocation(String location);
}
