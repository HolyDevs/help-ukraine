package help.ukraine.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import help.ukraine.app.security.dto.TokenVerificationError;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static help.ukraine.app.security.constants.SecurityConstants.ROLE_CLAIM;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Log4j2
@RequiredArgsConstructor
public class TokenDecoder {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${auth.secret}")
    private String authSecret;
    private final ObjectMapper objectMapper;

    public boolean hasAuthHeaderProperFormat(String authHeader) {
        return Objects.nonNull(authHeader) && authHeader.startsWith(BEARER_PREFIX);
    }

    public void fillResponseWithTokenVerificationError(HttpServletResponse response, String msg) throws IOException {
        log.error(msg);
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        TokenVerificationError tokenVerificationError = new TokenVerificationError(msg);
        objectMapper.writeValue(response.getOutputStream(), tokenVerificationError);
    }

    public UsernamePasswordAuthenticationToken decodeToken(String authHeader) {
        String token = authHeader.substring(BEARER_PREFIX.length());
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Optional<String> roleOptional = Optional.ofNullable(decodedJWT.getClaim(ROLE_CLAIM).asString());
        roleOptional.ifPresent(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }


}