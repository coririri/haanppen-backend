package com.hanpyeon.academyapi.security.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.exception.ExceptionResponseBody;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.exception.ExpiredJwtAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

public class JwtEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCode errorCode;
        if (authException.getClass().isAssignableFrom(ExpiredJwtAuthenticationException.class)) {
            // JWT 만료일 경우
            errorCode = ErrorCode.JWT_EXPIRED_EXCEPTION;
        } else {
            // 위에 인증되지 않았을 경우
            errorCode = ErrorCode.AUTHENTICATION_FAILED_EXCEPTION;
        }

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), ExceptionResponseBody.of(errorCode, authException.getMessage()));
    }
}
