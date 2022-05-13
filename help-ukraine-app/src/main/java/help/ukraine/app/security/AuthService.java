package help.ukraine.app.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class AuthService {
    public boolean checkIfAuthBelongsToUser(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) &&
                authentication.getName().equals(email);
    }

    public void updateCredentials(String email, String password, String oAuthRole) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(oAuthRole);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(email, password, List.of(authority));
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
