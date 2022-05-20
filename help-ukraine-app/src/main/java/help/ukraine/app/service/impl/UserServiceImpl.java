package help.ukraine.app.service.impl;

import help.ukraine.app.data.HostEntity;
import help.ukraine.app.data.RefugeeEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.exception.UserEmailNotUniqueException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.HostRepository;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {
    private static final String MISSING_USER_ID_MSG = "There is no user with id %d";
    private static final String MISSING_USER_EMAIL_MSG = "There is no user with email %s";
    private static final String FETCHED_USER_MSG = "User with id %d fetched";
    private static final String DELETED_USER_MSG = "User with id %d deleted";
    private static final String MODIFIED_USER_MSG = "User with id %d modified";
    private static final String CREATED_USER_MSG = "User with id %d created";
    private static final String USER_EMAIL_NOT_UNIQUE_MSG = "User with email %s already exists";
    private static final String USER_ID_ALREADY_EXISTS_MSG = "User with id %d already exists";

    private final MapperFacade userMapperFacade;
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final RefugeeRepository refugeeRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserModel getUserById(Long id) throws UserNotExistsException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException(String.format(MISSING_USER_ID_MSG, id)));
        log.info(String.format(FETCHED_USER_MSG, userEntity.getId()));
        return userMapperFacade.map(userEntity, UserModel.class);
    }

    @Override
    public UserModel getUserByEmail(String email) throws UserNotExistsException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(String.format(MISSING_USER_EMAIL_MSG, email)));
        log.info(String.format(FETCHED_USER_MSG, userEntity.getId()));
        return userMapperFacade.map(userEntity, UserModel.class);
    }

    @Override
    public UserModel createUser(UserModel userModel) throws UserEmailNotUniqueException {
        throwIfUserEmailNotUnique(userModel.getEmail());
        encodePassword(userModel);
        UserEntity userEntity = userMapperFacade.map(userModel, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        switch (userModel.getAccountType()) {
            case REFUGEE -> createRefugee(userEntity.getId());
            case HOST -> createHost(userEntity.getId());
        }
        log.info(String.format(CREATED_USER_MSG, userEntity.getId()));
        return userMapperFacade.map(userEntity, UserModel.class);
    }

    @Override
    public UserModel updateUser(UserModel userModel) throws UserNotExistsException, UserEmailNotUniqueException {
        UserEntity userEntity = userRepository.findById(userModel.getId())
                .orElseThrow(() -> new UserNotExistsException(String.format(MISSING_USER_ID_MSG, userModel.getId())));
        throwIfNewUserEmailNotUnique(userEntity.getEmail(), userModel.getEmail());
        if (shouldPasswordBeModified(userModel, userEntity)) {
            encodePassword(userModel);
        } else {
            userModel.setPassword(userEntity.getHashedPassword());
        }
        userEntity = userMapperFacade.map(userModel, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        log.info(String.format(MODIFIED_USER_MSG, userModel.getId()));
        return userMapperFacade.map(userEntity, UserModel.class);
    }

    @Override
    public void deleteUserById(Long id) throws UserNotExistsException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException(String.format(MISSING_USER_ID_MSG, id)));
        switch (userEntity.getAccountType()) {
            case HOST -> hostRepository.deleteById(userEntity.getId());
            case REFUGEE -> refugeeRepository.deleteById(userEntity.getId());
        }
        userRepository.delete(userEntity);
        log.info(String.format(DELETED_USER_MSG, id));
    }

    private void createRefugee(Long userEntityId) {
        RefugeeEntity refugeeEntity = RefugeeEntity.builder().userId(userEntityId).build();
        refugeeRepository.save(refugeeEntity);
    }

    private void createHost(Long userEntityId) {
        HostEntity hostEntity = HostEntity.builder().userId(userEntityId).build();
        hostRepository.save(hostEntity);
    }

    private void encodePassword(UserModel userModel) {
        String password = userModel.getPassword();
        userModel.setPassword(passwordEncoder.encode(password));
    }

    private void throwIfUserEmailNotUnique(String email) throws UserEmailNotUniqueException {
        if (!userRepository.existsByEmail(email)) {
            return;
        }
        String msg = String.format(USER_EMAIL_NOT_UNIQUE_MSG, email);
        log.error(msg);
        throw new UserEmailNotUniqueException(msg);
    }

    private void throwIfNewUserEmailNotUnique(String oldEmail, String newEmail) throws UserEmailNotUniqueException {
        if (oldEmail.equals(newEmail)) {
            return;
        }
        throwIfUserEmailNotUnique(newEmail);
    }

    private boolean shouldPasswordBeModified(UserModel userModel, UserEntity userEntity) {
        return !userModel.getPassword().equals(userEntity.getHashedPassword()) &&
                !passwordEncoder.matches(userModel.getPassword(), userEntity.getHashedPassword());
    }


}