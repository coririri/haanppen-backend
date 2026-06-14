package com.hanpyeon.academyapi.security.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.exception.ExceptionResponseBody;
import com.hanpyeon.academyapi.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.DENIED_EXCEPTION;
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), ExceptionResponseBody.of(errorCode, accessDeniedException.getMessage()));
    }
}
