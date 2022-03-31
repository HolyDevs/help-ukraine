package help.ukraine.app.service;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserModel getUser(String id) throws DataNotExistsException;
    UserModel saveUser(UserModel userModel);
}
