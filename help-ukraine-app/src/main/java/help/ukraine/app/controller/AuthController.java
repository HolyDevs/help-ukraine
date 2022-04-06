package help.ukraine.app.controller;

import help.ukraine.app.security.TokenDecoder;
import help.ukraine.app.security.TokenGenerator;
import help.ukraine.app.security.constants.AuthMessages;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.dto.GeneratedToken;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final TokenGenerator tokenGenerator;
    private final TokenDecoder tokenDecoder;
    private final UserService userService;

    @PostMapping(AuthUrls.LOGIN_URL)
    public void login() {}

    @GetMapping(AuthUrls.REFRESH_TOKEN_URL)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Token renewal attempt for authorization header: {}", authHeader);
        if (!tokenDecoder.hasAuthHeaderProperFormat(authHeader)) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthMessages.IMPROPER_FORMAT_AUTH_HEADER_MSG);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = tokenDecoder.decodeToken(authHeader);
            String issuer = request.getRequestURL().toString();
            User user = userService.loadUserByUsername(authenticationToken.getName());
            GeneratedToken generatedToken = tokenGenerator.generateToken(user, issuer);
            log.info("Successful token renewal for user: {}", user.getUsername());
            tokenGenerator.fillResponseWithGeneratedToken(generatedToken, response);
        } catch (Exception e) {
            tokenDecoder.fillResponseWithTokenVerificationError(response, AuthMessages.ACCESS_TOKEN_FAIL);
        }
    }
}
