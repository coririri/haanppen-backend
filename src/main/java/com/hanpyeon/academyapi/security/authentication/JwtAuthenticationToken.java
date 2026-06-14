package com.hanpyeon.academyapi.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final MemberPrincipal principals;

    private JwtAuthenticationToken(String token, MemberPrincipal memberPrincipal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Jwt 토큰 값은 null 일 수 없습니다." + token);
        }
        this.token = token;
        this.principals = memberPrincipal;
    }

    public static JwtAuthenticationToken authenticated(String token, MemberPrincipal memberPrincipal, Collection<? extends GrantedAuthority> authorities) {
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(token, memberPrincipal, authorities);
        authentication.setAuthenticated(true);
        return authentication;
    }

    public static JwtAuthenticationToken unauthenticated(String token) {
        return new JwtAuthenticationToken(token, null, null);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principals;
    }
}
