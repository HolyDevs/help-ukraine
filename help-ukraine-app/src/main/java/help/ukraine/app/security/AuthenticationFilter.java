package help.ukraine.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import static help.ukraine.app.security.SecurityConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final int ACCESS_TOKEN_EXPIRATION = 10 * 60 * 1000;
    private static final int REFRESH_TOKEN_EXPIRATION = 30 * 60 * 1000;

    private final String authSecret;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        log.info("Authentication attempt for user {}", username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        String issuer = request.getRequestURL().toString();
        String accessToken = generateAccessToken(user, issuer, algorithm);
        String refreshToken = generateRefreshToken(user, issuer, algorithm);
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), tokenResponse);
    }

    private String generateAccessToken(User user, String issuer, Algorithm algorithm) {
        Optional<GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
        String role = null;
        if (authority.isPresent()) {
            role = authority.get().getAuthority();
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .withIssuer(issuer)
                .withClaim(ROLE_CLAIM, role)
                .sign(algorithm);
    }

    private String generateRefreshToken(User user, String issuer, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
