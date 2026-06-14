package com.hanpyeon.academyapi.aspect.log;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Component
public class LogInterceptor implements HandlerInterceptor {
    static final String userIdentifier = "memberId";
    static final String requestIdentifier = "requestId";
    static final String clientIp = "clientIp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (!Objects.isNull(principal)) {
            Authentication authentication = (Authentication) principal;
            MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
            MDC.put(userIdentifier, memberPrincipal.memberId().toString());
        }
        MDC.put(requestIdentifier, UUID.randomUUID().toString());
        MDC.put(clientIp, request.getRemoteAddr());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
