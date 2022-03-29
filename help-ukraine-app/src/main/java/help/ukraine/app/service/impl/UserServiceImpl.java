package help.ukraine.app.service.impl;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.service.UserService;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private static final String MISSING_USER_MSG = "There is no user with id %s";
    private static final String FETCHED_USER_MSG = "User with id %s fetched";

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private UserRepository userRepository;


    public UserModel getUser(String id) throws DataNotExistsException {
        Optional<UserEntity> optional = userRepository.findById(id);
        throwIfMissingUser(optional, id);
        log.info(String.format(FETCHED_USER_MSG, id));
        return mapperFacade.map(optional.get(), UserModel.class);
    }

    private void throwIfMissingUser(Optional<UserEntity> optional, String id) throws DataNotExistsException {
        if (optional.isPresent()) {
            return;
        }
        String msg = String.format(MISSING_USER_MSG, id);
        log.error(msg);
        throw new DataNotExistsException(msg);
    }
}
