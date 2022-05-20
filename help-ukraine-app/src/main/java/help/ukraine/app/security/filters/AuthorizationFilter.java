package help.ukraine.app.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import help.ukraine.app.controller.UserController;
import help.ukraine.app.security.TokenDecoder;
import help.ukraine.app.security.constants.AuthMessages;
import help.ukraine.app.security.constants.AuthUrls;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
        if (isNoAuthEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization attempt for authorization header: {}", authHeader);
        if (!tokenDecoder.hasAuthHeaderProperFormat(authHeader)) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthMessages.IMPROPER_FORMAT_AUTH_HEADER_MSG);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = tokenDecoder.decodeToken(authHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Token successfully decoded during authorization for user - email : {}, id : {}",
                    authenticationToken.getName(), authenticationToken.getDetails());
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthMessages.ACCESS_TOKEN_FAIL);
        }
    }

    private boolean isNoAuthEndpoint(HttpServletRequest request) {
        return !request.getServletPath().startsWith(AuthUrls.BACKEND_ROOT) ||
                isUserRegistrationEndpoint(request) ||
                AuthUrls.NO_AUTH_URLS.contains(request.getServletPath());
    }

    private boolean isUserRegistrationEndpoint(HttpServletRequest request) {
        return request.getServletPath().equals(UserController.USER_ENDPOINT) &&
                HttpMethod.POST.matches(request.getMethod());

    }
}
