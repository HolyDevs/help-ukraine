package help.ukraine.app.security.service;

import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.model.UserPrincipal;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserPrincipalService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserModel userModel = userService.getUserByEmail(email);
            return new UserPrincipal(userModel);
        } catch (UserNotExistsException e) {
            log.error(e);
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }
}
