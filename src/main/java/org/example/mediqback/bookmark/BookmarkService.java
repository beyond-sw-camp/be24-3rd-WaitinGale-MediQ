package org.example.mediqback.bookmark;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.bookmark.BookmarkRepository;
import org.example.mediqback.bookmark.model.Bookmark;
import org.example.mediqback.bookmark.model.BookmarkDto;
import org.example.mediqback.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    // 북마크 등록
    public BookmarkDto.Res register(User user, BookmarkDto.Reg dto) {
        Bookmark entity = bookmarkRepository.save(dto.toEntity(user));
        return BookmarkDto.Res.from(entity);
    }

    // 북마크 상세 조회
    public BookmarkDto.Res read(Long idx) {
        Bookmark bookmark = bookmarkRepository.findById(idx).orElseThrow(
                () -> new RuntimeException("북마크 X")
        );
        return BookmarkDto.Res.from(bookmark);
    }

    // 북마크 리스트
    public List<BookmarkDto.Res> list(User user) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);

        return bookmarks.stream().map(BookmarkDto.Res::from).toList();
    }

    // 북마크 삭제
    @Transactional
    public void delete(Long idx) {
        Bookmark bookmark = bookmarkRepository.findById(idx).orElseThrow(
                () -> new RuntimeException("해당 북마크를 찾을 수 없습니다.")
        );

        bookmarkRepository.delete(bookmark);
    }

}