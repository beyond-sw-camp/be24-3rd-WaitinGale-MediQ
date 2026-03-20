package org.example.mediqback.bookmark;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.bookmark.model.BookmarkDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.example.mediqback.bookmark.BookmarkService;

import java.util.List;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
@RequestMapping("/bookmark")
@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    // 북마크 등록
    @PostMapping("/register")
    public ResponseEntity<BookmarkDto.Res> register(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @RequestBody BookmarkDto.Reg dto) {
        BookmarkDto.Res response = bookmarkService.register(userDetails.toEntity(), dto);
        return ResponseEntity.ok(response);
    }

    // 북마크 상세 조회
    @GetMapping("/{idx}")
    public ResponseEntity<BookmarkDto.Res> readBookmark(
            @PathVariable Long idx) {
        BookmarkDto.Res response = bookmarkService.read(idx);
        return ResponseEntity.ok(response);
    }

    // 북마크 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<BookmarkDto.Res>> getBookmarkList(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        List<BookmarkDto.Res> response = bookmarkService.list(userDetails.toEntity());
        return ResponseEntity.ok(response);
    }

    // 북마크 삭제 기능
    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<String> deleteBookmark(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @PathVariable Long idx) {

        bookmarkService.delete(idx);

        return ResponseEntity.ok("북마크가 삭제되었습니다.");
    }


}