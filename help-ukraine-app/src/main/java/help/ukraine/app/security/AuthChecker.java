package help.ukraine.app.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Log4j2
public class AuthChecker {
    public boolean checkIfAuthBelongsToUser(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) &&
                authentication.getName().equals(email);
    }
}
