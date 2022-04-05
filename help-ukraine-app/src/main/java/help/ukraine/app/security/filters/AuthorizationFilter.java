package help.ukraine.app.security.filters;

import help.ukraine.app.security.TokenDecoder;
import help.ukraine.app.security.constants.AuthTokenContents;
import help.ukraine.app.security.constants.AuthUrls;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization attempt for authorization header: {}", authHeader);
        if (!tokenDecoder.hasAuthHeaderProperFormat(authHeader)) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthTokenContents.IMPROPER_FORMAT_AUTH_HEADER_MSG);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = tokenDecoder.decodeToken(authHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Token successfully decoded during authorization for user: {}", authenticationToken.getName());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthTokenContents.ACCESS_TOKEN_FAIL);
        }
    }

    private boolean isLoginOrRefreshRequest(HttpServletRequest request) {
        return request.getServletPath().equals(AuthUrls.LOGIN_URL)
                || request.getServletPath().equals(AuthUrls.REFRESH_TOKEN_URL);
    }
}
