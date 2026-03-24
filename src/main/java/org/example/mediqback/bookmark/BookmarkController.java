package org.example.mediqback.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.bookmark.model.BookmarkDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookmark API", description = "북마크 관련 API")
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
    @Operation(summary = "북마크 등록", description = "새로운 장소를 북마크로 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 등록 성공")
    })
    @PostMapping("/register")
    public ResponseEntity<BookmarkDto.Res> register(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @RequestBody BookmarkDto.Reg dto) {
        BookmarkDto.Res response = bookmarkService.register(userDetails.toEntity(), dto);
        return ResponseEntity.ok(response);
    }

    // 북마크 상세 조회
    @Operation(summary = "북마크 상세 조회", description = "특정 북마크의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 조회 성공")
    })
    @GetMapping("/{idx}")
    public ResponseEntity<BookmarkDto.Res> readBookmark(
            @PathVariable Long idx) {
        BookmarkDto.Res response = bookmarkService.read(idx);
        return ResponseEntity.ok(response);
    }

    // 북마크 전체 조회
    @Operation(summary = "북마크 전체 조회", description = "현재 로그인한 사용자의 모든 북마크를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 목록 조회 성공")
    })
    @GetMapping("/list")
    public ResponseEntity<List<BookmarkDto.Res>> getBookmarkList(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        List<BookmarkDto.Res> response = bookmarkService.list(userDetails.toEntity());
        return ResponseEntity.ok(response);
    }

    // 북마크 삭제 기능
    @Operation(summary = "북마크 삭제", description = "특정 북마크를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 삭제 성공")
    })
    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<String> deleteBookmark(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @PathVariable Long idx) {

        bookmarkService.delete(idx);

        return ResponseEntity.ok("북마크가 삭제되었습니다.");
    }
}