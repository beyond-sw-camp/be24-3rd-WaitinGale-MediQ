package org.example.mediqback.finduserinfo;

import org.example.mediqback.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FindUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialIdAndProvider(String socialId, String provider);

    @Query("SELECT u.email FROM User u WHERE u.name = :name")
    List<String> findEmailsByName(@Param("name") String name);
}