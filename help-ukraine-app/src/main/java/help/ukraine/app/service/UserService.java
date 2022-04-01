package help.ukraine.app.service;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
    UserModel getUserByEmail(String email) throws DataNotExistsException;
    UserModel saveUser(UserModel userModel);
}
