package com.hanpyeon.academyapi.security.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationTokenTest {
    @Mock
    MemberPrincipal memberPrincipal;

    @Test
    void 비인증_토큰_테스트() {
        JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.unauthenticated("12");
        assertThat(jwtAuthenticationToken.isAuthenticated()).isFalse();
    }
    @Test
    void 인증_객체_테스트() {
        JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.authenticated("12", memberPrincipal, null);
        assertThat(jwtAuthenticationToken.isAuthenticated()).isTrue();
    }

    @Test
    void 토큰_에러처리_테스트() {
        assertThatThrownBy(() -> JwtAuthenticationToken.unauthenticated(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> JwtAuthenticationToken.authenticated(null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> JwtAuthenticationToken.authenticated(null, memberPrincipal, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}