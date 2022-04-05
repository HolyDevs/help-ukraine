package help.ukraine.app.security.filters;

import help.ukraine.app.security.TokenDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.ukraine.app.security.constants.SecurityConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Log4j2
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenDecoder tokenDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isLoginOrRefreshRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader(AUTHORIZATION);
        log.info("Authorization attempt for authorization header: {}", authHeader);
        if (!tokenDecoder.hasAuthHeaderProperFormat(authHeader)) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, IMPROPER_FORMAT_AUTH_HEADER_MSG);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = tokenDecoder.decodeToken(authHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Token successfully decoded during authorization for user: {}", authenticationToken.getName());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, ACCESS_TOKEN_FAIL);
        }
    }

    private boolean isLoginOrRefreshRequest(HttpServletRequest request) {
        return request.getServletPath().equals(LOGIN_URL)
                || request.getServletPath().equals(REFRESH_TOKEN_URL);
    }
}
