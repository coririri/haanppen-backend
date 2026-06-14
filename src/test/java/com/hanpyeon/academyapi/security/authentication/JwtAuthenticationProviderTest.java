package com.hanpyeon.academyapi.security.authentication;

import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.exception.IllegalJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @Mock
    JwtUtils jwtUtils;
    @Mock
    Claims claims;
    @Mock
    JwtAuthenticationToken jwtAuthenticationToken;

    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private static String TEST_TOKEN_STRING = "forTest";


    @BeforeEach
    void init() {
        jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtUtils);
        jwtAuthenticationToken = JwtAuthenticationToken.unauthenticated(TEST_TOKEN_STRING);
        claims = jwtUtils.parseToken(TEST_TOKEN_STRING);
    }

    @Test
    void 성공처리_테스트() {
        Mockito.lenient().when(jwtUtils.getMemberId(claims)).thenReturn(Optional.of(12832l));
        Mockito.lenient().when(jwtUtils.getMemberName(claims)).thenReturn(Optional.of("Heejong"));
        Mockito.lenient().when(jwtUtils.getMemberRole(claims)).thenReturn(Optional.of(Role.STUDENT));
        assertThat(jwtAuthenticationProvider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(JwtAuthenticationToken.class);
    }

    @Test
    void ID_에러처리_테스트() {
        Mockito.lenient().when(jwtUtils.getMemberId(claims)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jwtAuthenticationProvider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(IllegalJwtAuthenticationException.class)
                .hasMessage("Cannot Find MemberId");
    }

    @Test
    void Name_에러처리_테스트() {
        Mockito.lenient().when(jwtUtils.getMemberId(claims)).thenReturn(Optional.of(Long.valueOf(12)));
        Mockito.lenient().when(jwtUtils.getMemberName(claims)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jwtAuthenticationProvider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(IllegalJwtAuthenticationException.class)
                .hasMessage("Cannot Find MemberName");
    }

    @Test
    void Role_에러처리_테스트() {
        Mockito.lenient().when(jwtUtils.getMemberId(claims)).thenReturn(Optional.of((long) 12));
        Mockito.lenient().when(jwtUtils.getMemberName(claims)).thenReturn(Optional.of("test"));
        Mockito.lenient().when(jwtUtils.getMemberRole(claims)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jwtAuthenticationProvider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(IllegalJwtAuthenticationException.class)
                .hasMessage("Cannot Find MemberRole");
    }

    @Test
    void 지원여부_성공_테스트() {
        assertThat(jwtAuthenticationProvider.supports(jwtAuthenticationToken.getClass())).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(classes = {Authentication.class, UsernamePasswordAuthenticationToken.class})
    void 지원여부_실패_테스트(Class<?> authentication) {
        assertThat(jwtAuthenticationProvider.supports(authentication.getClass())).isEqualTo(false);
    }
}
