package org.example.mediqback.bookmark;

import org.example.mediqback.bookmark.model.Bookmark;
import org.example.mediqback.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByIdxAndUser(Long idx, User user);
}