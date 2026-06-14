package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.TimeProvider;
import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.exceptions.InvalidPasswordException;
import com.hanpyeon.academyapi.account.exceptions.ReLoginRequiredException;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final AccountLoader accountLoader;
    private final JwtUtils jwtUtils;
    private final AccountLockService accountLockService;
    private final TimeProvider timeProvider;

    @WarnLoggable
    public JwtDto provideJwtByLogin(final String phoneNumber, final Password password) {
        final Account account = accountLoader.loadAccount(phoneNumber);
        final LocalDateTime now = timeProvider.getCurrentTime();

        if (!accountLockService.checkAllowedToLogin(account, now)) {
            throw new AccountException("잠긴 계정입니다.", ErrorCode.ACCOUNT_POLICY);
        }
        if (!account.isMatchPassword(password)) {
            accountLockService.updateLoginFailedInfo(account, now);
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
        accountLockService.unlock(account, now);
        return createJwtDto(account);
    }

    /**
     * @param refreshToken jwtrefreshToken
     * @return refreshToken을 통한 로그인 성공 시, 계정 잠금이 해제됩니다.
     */
    @WarnLoggable
    public JwtDto provideJwtByRefreshToken(final String refreshToken) {
        final LocalDateTime now = timeProvider.getCurrentTime();
        try {
            final Claims claims = jwtUtils.parseToken(refreshToken);
            final Long memberId = jwtUtils.getMemberId(claims)
                    .orElseThrow(() -> new JwtException("잘못된 JWT"));

            final Account account = accountLoader.loadAccount(memberId);
            accountLockService.unlock(account, now);
            return createJwtDto(account);
        } catch (JwtException exception) {
            throw new ReLoginRequiredException("재 로그인 필요", ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private JwtDto createJwtDto(final Account member) {
        String userName = member.getAccountName().getName();
        String accessToken = jwtUtils.generateAccessToken(member.getId(), member.getAccountRole().getRole(),
                member.getAccountName().getName());
        String refreshToken = jwtUtils.generateRefreshToken(member.getId());
        Role role = member.getAccountRole().getRole();
        return new JwtDto(userName, accessToken, refreshToken, role);
    }
}
