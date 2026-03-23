package org.example.mediqback.finduserinfo;

import org.example.mediqback.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FindUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialIdAndProvider(String socialId, String provider);

    List<User> findByName(String name);
}