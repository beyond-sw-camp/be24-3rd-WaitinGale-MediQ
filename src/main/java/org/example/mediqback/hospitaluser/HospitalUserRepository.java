package org.example.mediqback.hospitaluser;

import org.example.mediqback.hospitaluser.model.HospitalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalUserRepository extends JpaRepository<HospitalUser, Long> {
    Optional<HospitalUser> findByEmail(String email);
}