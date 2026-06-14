package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.security.PasswordHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PasswordHandlerTest {
    private static final String OTHER = "OTHER";
    private static final String DEFAULT = "DEFAULT";
    @Value("${service.password.default}")
    private String testPassword;

    @Mock
    PasswordEncoder passwordEncoder;
    private PasswordHandler passwordHandler;

    @BeforeEach
    void initPasswordHandler() {
        this.passwordHandler = new PasswordHandler(passwordEncoder);
    }

    @ParameterizedTest
    @MethodSource("provideIllegalPasswords")
    void 비밀번호_초기값_테스트(String inputPassword) {
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.eq(testPassword))).thenReturn(DEFAULT);

        Assertions.assertThat(passwordHandler.getEncodedPassword(inputPassword)).isEqualTo(DEFAULT);
        Assertions.assertThat(passwordHandler.getEncodedPassword(null)).isEqualTo(DEFAULT);
    }

    @ParameterizedTest
    @MethodSource("provideLegalPasswords")
    void 비밀번호_사용자값_테스트(String inputPassword) {
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.argThat(arg -> !arg.equals(testPassword)))).thenReturn(OTHER);

        Assertions.assertThat(passwordHandler.getEncodedPassword(inputPassword)).isEqualTo(OTHER);
    }

    static Stream<Arguments> provideIllegalPasswords() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("\t"),
                Arguments.of("  \n")
        );
    }

    static Stream<Arguments> provideLegalPasswords() {
        return Stream.of(
                Arguments.of("qwerwqer"),
                Arguments.of("qwerdas"),
                Arguments.of("wefsd123tfe"),
                Arguments.of("efwye5"),
                Arguments.of("wqereler")
        );
    }
}
