package help.ukraine.app.service;

import help.ukraine.app.exception.UserEmailNotUniqueException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.UserModel;

public interface UserService {
    UserModel getUserByEmail(String email) throws UserNotExistsException;
    UserModel getUserById(Long id) throws UserNotExistsException;
    UserModel updateUser(UserModel userModel) throws UserNotExistsException, UserEmailNotUniqueException;
    void deleteUserById(Long id) throws UserNotExistsException;
    UserModel createUser(UserModel userModel) throws UserEmailNotUniqueException;
}
