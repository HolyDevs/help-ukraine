package help.ukraine.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import help.ukraine.app.security.constants.AuthTokenContents;
import help.ukraine.app.security.dto.GeneratedToken;
import help.ukraine.app.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class TokenGenerator {
    @Value("${auth.secret}")
    private String authSecret;
    private final ObjectMapper objectMapper;

    public void fillResponseWithGeneratedToken(GeneratedToken generatedToken, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), generatedToken);
    }

    public GeneratedToken generateToken(UserPrincipal userPrincipal, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        String accessToken = generateAccessToken(userPrincipal, issuer, algorithm);
        String refreshToken = generateRefreshToken(userPrincipal, issuer, algorithm);
        return GeneratedToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateAccessToken(UserPrincipal userPrincipal, String issuer, Algorithm algorithm) {
        Optional<GrantedAuthority> authority = userPrincipal.getAuthorities().stream().findFirst();
        String role = null;
        if (authority.isPresent()) {
            role = authority.get().getAuthority();
        }
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthTokenContents.ACCESS_TOKEN_EXPIRATION))
                .withIssuer(issuer)
                .withClaim(AuthTokenContents.ROLE_CLAIM, role)
                .withClaim(AuthTokenContents.ID_CLAIM, userPrincipal.getId())
                .sign(algorithm);
    }

    private String generateRefreshToken(UserPrincipal userPrincipal, String issuer, Algorithm algorithm) {
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthTokenContents.REFRESH_TOKEN_EXPIRATION))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
