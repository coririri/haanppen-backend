package com.hanpyeon.academyapi.security;

import com.hanpyeon.academyapi.security.exceptionhandler.AccessDeniedHandler;
import com.hanpyeon.academyapi.security.exceptionhandler.JwtEntryPointHandler;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@Configuration
@SecurityScheme(
        name = "jwtAuth", // API 문서에서 참조될 보안 스키마의 이름
        type = SecuritySchemeType.HTTP, // 보안 스키마의 타입
        scheme = "bearer", // HTTP 스키마
        bearerFormat = "JWT" // 베어러 포맷
)
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .sessionManagement(session -> session.disable())
                .exceptionHandling(config -> {
                    config.authenticationEntryPoint(new JwtEntryPointHandler());
                    config.accessDeniedHandler(new AccessDeniedHandler());
                })
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), AuthorizationFilter.class)
                .authorizeHttpRequests(request -> {
/////////////////////////////////////// 개발 중 열어둠 //////////////////////////////////////
                    request.requestMatchers(request1 -> request1.isUserInRole(Role.ADMIN.getSecurityRole()))
                            .permitAll();
                    request.requestMatchers("/swagger-ui/**")
                            .permitAll();
                    request.requestMatchers("/swagger-ui")
                            .permitAll();
                    request.requestMatchers("/v3/api-docs/**")
                            .permitAll();

                    request.requestMatchers("/api/login")
                            .permitAll();
                    request.requestMatchers("/api/login/refresh")
                            .permitAll();
                    request.requestMatchers("/api/logout")
                            .authenticated();
//////////////////////////////////////////////////////////////////////////////////////////
                    // accounts
                    request.requestMatchers(HttpMethod.POST, "/api/accounts/password/verification")
                            .permitAll();
                    request.requestMatchers(HttpMethod.PUT, "/api/accounts/password/verification")
                            .permitAll();

                    request.requestMatchers(HttpMethod.GET, "/api/accounts/my")
                            .authenticated();

                    request.requestMatchers(HttpMethod.POST, "/api/accounts")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.DELETE, "/api/accounts")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.PUT, "/api/accounts/teacher")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.PUT, "/api/accounts/student")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );

                    request.requestMatchers(HttpMethod.POST, "/api/banners")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole());
                    request.requestMatchers(HttpMethod.PUT, "/api/banners")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/banners")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole());

                    // 온라인 강의
                    request.requestMatchers(HttpMethod.POST, "/api/online-courses")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.POST, "/api/online-courses")
                                    .hasAnyAuthority(
                                            Role.ADMIN.getSecurityRole(),
                                            Role.MANAGER.getSecurityRole(),
                                            Role.TEACHER.getSecurityRole()
                                    );
                    request.requestMatchers(HttpMethod.PUT,"/api/online-courses/**")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.DELETE, "/api/online-courses/*")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );

                    request.requestMatchers("/api/images/**")
                            .permitAll();

                    request.requestMatchers(HttpMethod.POST, "/api/board/comments")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/comments/*")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.PATCH, "/api/board/comments/*")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.PUT, "/api/board/questions/*")
                            .authenticated();
                    request.requestMatchers(HttpMethod.POST, "/api/board/questions")
                            .hasAuthority(
                                    Role.STUDENT.getSecurityRole());
//                    request.requestMatchers(HttpMethod.GET, "/api/board/questions/*")
//                            .authenticated();
//                    request.requestMatchers(HttpMethod.GET, "/api/board/questions")
//                            .authenticated();
//                    request.requestMatchers(HttpMethod.PATCH, "/api/board/questions/*")
//                            .hasAnyAuthority(
//                                    Role.STUDENT.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/questions/*")
                            .hasAnyAuthority(
                                    Role.STUDENT.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.GET, "/api/courses/my")
                            .authenticated();


                    // 반 삭제 API는 매니저만 사용가능 하도록 구현
                    request.requestMatchers(HttpMethod.DELETE, "/api/manage/courses/*")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());

                    // DELETE Method 경우 아래 Matcher 는 사용되지 않음
                    request.requestMatchers("/api/manage/courses/**")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());

                    request.requestMatchers(HttpMethod.GET, "/api/media/*")
                            .permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/media/stream/*")
                            .permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/courses")
                            .authenticated();
                    request.requestMatchers(HttpMethod.GET, "/api/courses/memos")
                            .authenticated();
                    request.requestMatchers(HttpMethod.POST, "/api/course/memo/media")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole());


                    request.requestMatchers(HttpMethod.POST, "/api/directories")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );

                    request.requestMatchers(HttpMethod.GET, "/api/directories")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.PUT, "/api/directories")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );

                    // 온라인 카테고리
                    request.requestMatchers(HttpMethod.POST, "/api/online-courses/category")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.DELETE, "/api/online-courses/category/*")
                            .hasAnyAuthority(
                                    Role.ADMIN.getSecurityRole(),
                                    Role.MANAGER.getSecurityRole()
                            );

                    request.requestMatchers(HttpMethod.GET)
                            .authenticated();
                    // 404 NOT FOUND EXCEPTION
                    request.anyRequest().permitAll();
                })
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    PasswordEncoder passwordEncoder(
            @Value("${server.password.encryption.version}") String encryptVersion,
            @Value("${server.password.encryption.strength}") int strength)
    {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.valueOf(encryptVersion), strength);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
