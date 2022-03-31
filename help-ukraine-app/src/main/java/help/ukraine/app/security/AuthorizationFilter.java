package help.ukraine.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static help.ukraine.app.security.SecurityConstants.LOGIN_URL;
import static help.ukraine.app.security.SecurityConstants.ROLE_CLAIM;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String IMPROPER_FORMAT_AUTH_HEADER_MSG = "Authorization header has improper format";
    private static final String ACCESS_TOKEN_FAIL = "Cannot decode access token - %s";

    private final String authSecret;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setStatus(FORBIDDEN.value());
        if (request.getServletPath().equals(LOGIN_URL)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader(AUTHORIZATION);
        if (!hasAuthHeaderProperFormat(authHeader)) {
            setErrorResponse(response, IMPROPER_FORMAT_AUTH_HEADER_MSG);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = decodeToken(authHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e)  {
            String msg = String.format(ACCESS_TOKEN_FAIL, e.getMessage());
            setErrorResponse(response, msg);
        }
    }

    private UsernamePasswordAuthenticationToken decodeToken(String authHeader) {
        String token = authHeader.substring(BEARER_PREFIX.length());
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String role = decodedJWT.getClaim(ROLE_CLAIM).asString();
        Collection<SimpleGrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private boolean hasAuthHeaderProperFormat(String authHeader) {
        return Objects.nonNull(authHeader) && authHeader.startsWith(BEARER_PREFIX);
    }

    private void setErrorResponse(HttpServletResponse response, String msg) throws IOException {
        log.error(msg);
        ErrorResponse errorResponse = new ErrorResponse(msg);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
    }


}
