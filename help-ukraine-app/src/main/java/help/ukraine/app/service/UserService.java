package help.ukraine.app.service;

import help.ukraine.app.exception.MissingDataException;
import help.ukraine.app.model.UserModel;

public interface UserService {
    UserModel getUser(String id) throws MissingDataException;
}
