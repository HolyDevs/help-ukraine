package help.ukraine.app.security.filters;

import help.ukraine.app.security.TokenGenerator;
import help.ukraine.app.security.constants.AuthTokenContents;
import help.ukraine.app.security.dto.GeneratedToken;
import help.ukraine.app.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter(AuthTokenContents.EMAIL_PARAMETER);
        String password = request.getParameter(AuthTokenContents.PASSWORD_PARAMETER);
        log.info("Authentication attempt for user - email : {}", email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
        String issuer = request.getRequestURL().toString();
        GeneratedToken generatedToken = tokenGenerator.generateToken(userPrincipal, issuer);
        log.info("Successful authentication for user - email : {}; id {}", userPrincipal.getUsername(), userPrincipal.getId());
        tokenGenerator.fillResponseWithGeneratedToken(generatedToken, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        String username = request.getParameter(AuthTokenContents.EMAIL_PARAMETER);
        log.info("Authentication failed for user {} because of: {}", username, failed.getMessage());
    }
}
