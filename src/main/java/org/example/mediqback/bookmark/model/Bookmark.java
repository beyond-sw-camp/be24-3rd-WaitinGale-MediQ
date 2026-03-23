package org.example.mediqback.bookmark.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.common.model.BaseEntity;
import org.example.mediqback.user.model.User;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

// BaseEntity 상속 추가 (생성일, 수정일 자동화)
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String kakaoPlaceId; // 카카오맵의 place.id
    private String name;
    private String address;

    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

}
