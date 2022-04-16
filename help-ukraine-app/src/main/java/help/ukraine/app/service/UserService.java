package help.ukraine.app.service;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.exception.UserNoAccessException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthRoles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    UserModel fetchUser(String email) throws DataNotExistsException, UserNoAccessException;

    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    UserModel updateUser(UserModel userModel) throws UserNoAccessException, DataNotExistsException;

    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    void deleteUser(String email) throws DataNotExistsException, UserNoAccessException;

    UserModel createUser(UserModel userModel) throws UserAlreadyRegisteredException;

    boolean existsUser(String email);
}
