package help.ukraine.app.security.service;

import help.ukraine.app.security.constants.AuthMessages;
import help.ukraine.app.security.exception.UserNoAccessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class AuthService {

    public void throwIfAuthNotBelongToUser(Long id) throws UserNoAccessException {
        if (checkIfAuthBelongsToUser(id)) {
            return;
        }
        throw new UserNoAccessException(AuthMessages.USER_NO_ACCESS_MSG);
    }

    public void throwIfAuthNotBelongToUser(String email) throws UserNoAccessException {
        if (checkIfAuthBelongsToUser(email)) {
            return;
        }
        throw new UserNoAccessException(AuthMessages.USER_NO_ACCESS_MSG);
    }

    private boolean checkIfAuthBelongsToUser(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        String userEmail = (String) authentication.getPrincipal();
        return email.equals(userEmail);
    }

    private boolean checkIfAuthBelongsToUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        Long tokenUserId = (Long) authentication.getDetails();
        return id.equals(tokenUserId);
    }
}
