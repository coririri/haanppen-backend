package com.hanpyeon.academyapi.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @ParameterizedTest
    @MethodSource("provideArguments")
    void generateToken(Long id, Role role, String name) {
        String token = jwtUtils.generateAccessToken(id, role, name);
        assertThat(token).isNotBlank();
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void 토큰_추출_테스트(Long id, Role role, String name) {
        String token = jwtUtils.generateAccessToken(id, role, name);
        Claims claims = jwtUtils.parseToken(token);
        assertThat(jwtUtils.getMemberId(claims)).isEqualTo(Optional.ofNullable(id));
        assertThat(jwtUtils.getMemberRole(claims)).isEqualTo(Optional.ofNullable(role));
        assertThat(jwtUtils.getMemberName(claims)).isEqualTo(Optional.ofNullable(name));
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, Role.MANAGER, "lqkwsmd"),
                Arguments.of((long) 1, null, "lqkwsmd"),
                Arguments.of((long) 1, Role.MANAGER, null)
        );
    }
}