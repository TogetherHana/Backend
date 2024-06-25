package com.togetherhana.auth.jwt;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenExtractor {
    private static final String SCHEMA = "Bearer ";
    private static final String TOKEN_HEADER = "Authorization";
    private static final String REFRESH_HEADER = "Authorization-Refresh";
    private JwtTokenExtractor() {
    }

    public static String extractJwt(HttpServletRequest req) {
        String token = req.getHeader(TOKEN_HEADER);

        if (token != null && token.startsWith(SCHEMA)) {
            return token.replace(SCHEMA,"");
        }
        throw new BaseException(ErrorType.INVALID_JWT);
    }
    public static String extractRefresh(HttpServletRequest req) {
        String token = req.getHeader(REFRESH_HEADER);

        if (token != null && token.startsWith(SCHEMA)) {
            return token.replace(SCHEMA,"");
        }
        throw new BaseException(ErrorType.INVALID_JWT);
    }
}