package org.example.mediqback.user;


import org.example.mediqback.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialIdAndProvider(String socialId, String provider);
}