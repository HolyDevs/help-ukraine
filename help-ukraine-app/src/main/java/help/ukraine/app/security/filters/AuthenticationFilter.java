package help.ukraine.app.security.filters;

import help.ukraine.app.security.TokenGenerator;
import help.ukraine.app.security.dto.GeneratedToken;
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

import static help.ukraine.app.security.constants.SecurityConstants.PASSWORD_PARAMETER;
import static help.ukraine.app.security.constants.SecurityConstants.USERNAME_PARAMETER;

@Log4j2
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

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
        String issuer = request.getRequestURL().toString();
        GeneratedToken generatedToken = tokenGenerator.generateToken(user, issuer);
        log.info("Successful authentication for user: {}", user.getUsername());
        tokenGenerator.fillResponseWithGeneratedToken(generatedToken, response);
    }
}
