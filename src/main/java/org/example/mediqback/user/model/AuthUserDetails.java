package org.example.mediqback.user.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class AuthUserDetails implements UserDetails, OAuth2User {
    private Long idx;
    private String username;
    private String password;
    private boolean enable;
    private String role;

    // 소셜 로그인에 필요한 변수 추가
    private String name;
    private Map<String, Object> attributes;

    //  일반 로그인용 from 메서드 (attributes 없음)
    public static AuthUserDetails from(User entity) {
        return AuthUserDetails.builder()
                .idx(entity.getIdx())
                .username(entity.getEmail())
                .name(entity.getName())
                .password(entity.getPassword())
                .enable(entity.isEnable())
                .role(entity.getRole())
                .build();
    }

    //  소셜 로그인용 from 메서드 (attributes 포함)
    public static AuthUserDetails from(User entity, Map<String, Object> attributes) {
        return AuthUserDetails.builder()
                .idx(entity.getIdx())
                .username(entity.getEmail())
                .name(entity.getName())
                .password(entity.getPassword())
                .enable(entity.isEnable())
                .role(entity.getRole())
                .attributes(attributes) // 카카오 정보 추가
                .build();
    }

    //  OAuth2User 필수 메서드 구현
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    //  기존 UserDetails 필수 메서드
    @Override
    public boolean isEnabled() { return enable; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    //  엔티티 변환 메서드
    public User toEntity() {
        return User.builder()
                .idx(this.idx)
                .email(this.username)
                .name(this.name)
                .password(this.password)
                .enable(this.enable)
                .role(this.role)
                .build();
    }
}