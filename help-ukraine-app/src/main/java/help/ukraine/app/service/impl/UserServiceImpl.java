package help.ukraine.app.service.impl;

import help.ukraine.app.data.HostEntity;
import help.ukraine.app.data.RefugeeEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.HostRepository;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {
    private static final String MISSING_USER_MSG = "There is no user with email %s";
    private static final String FETCHED_USER_MSG = "User with email %s fetched";
    private static final String DELETED_USER_MSG = "User with email %s deleted";
    private static final String MODIFIED_USER_MSG = "User with email %s modified";
    private static final String CREATED_USER_MSG = "User with email %s created";
    private static final String USER_ALREADY_REGISTERED_MSG = "User with email %s is already registered";

    private final MapperFacade userMapperFacade;
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final RefugeeRepository refugeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserModel userModel = getUser(username);
            String oAuthRole = getOAuthRole(userModel.getAccountType());
            Collection<SimpleGrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(oAuthRole));
            return new User(userModel.getEmail(), userModel.getPassword(), authorities);
        } catch (DataNotExistsException e) {
            log.error(e);
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public UserModel getUser(String email) throws DataNotExistsException {
        Optional<UserEntity> optional = userRepository.findByEmail(email);
        throwIfMissingUser(optional, email);
        log.info(String.format(FETCHED_USER_MSG, email));
        return userMapperFacade.map(optional.get(), UserModel.class);
    }

    @Override
    public UserModel registerUser(UserModel userModel) throws UserAlreadyRegisteredException {
        throwIfUserAlreadyRegistered(userModel.getEmail());
        return createUser(userModel);
    }

    @Override
    public UserModel modifyUser(UserModel userModel) {
        Optional<UserEntity> optional = userRepository.findByEmail(userModel.getEmail());
        if (optional.isEmpty()) {
            return createUser(userModel);
        }
        UserEntity userEntity = optional.get();
        return updateUser(userModel, userEntity);
    }

    @Override
    public void deleteUser(String email) throws DataNotExistsException {
        Optional<UserEntity> optional = userRepository.findByEmail(email);
        throwIfMissingUser(optional, email);
        UserEntity userEntity = optional.get();
        switch (userEntity.getAccountType()) {
            case HOST -> hostRepository.deleteById(userEntity.getId());
            case REFUGEE -> refugeeRepository.deleteById(userEntity.getId());
        }
        log.info(String.format(DELETED_USER_MSG, email));
        userRepository.delete(userEntity);
    }

    private UserModel createUser(UserModel userModel) {
        encodePassword(userModel);
        UserEntity userEntity = userMapperFacade.map(userModel, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        switch (userModel.getAccountType()) {
            case REFUGEE -> createRefugee(userEntity.getId());
            case HOST -> createHost(userEntity.getId());
        }
        log.info(String.format(CREATED_USER_MSG, userModel.getEmail()));
        return userMapperFacade.map(userEntity, UserModel.class);
    }

    private UserModel updateUser(UserModel userModel, UserEntity userEntity) {
        if (shouldPasswordBeModified(userModel, userEntity)) {
            encodePassword(userModel);
        } else {
            userModel.setPassword(userEntity.getHashedPassword());
        }
        userEntity = userMapperFacade.map(userModel, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        log.info(String.format(MODIFIED_USER_MSG, userModel.getEmail()));
        return userMapperFacade.map(userEntity, UserModel.class);
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

    private void throwIfMissingUser(Optional<UserEntity> optional, String email) throws DataNotExistsException {
        if (optional.isPresent()) {
            return;
        }
        String msg = String.format(MISSING_USER_MSG, email);
        log.error(msg);
        throw new DataNotExistsException(msg);
    }

    private void throwIfUserAlreadyRegistered(String email) throws UserAlreadyRegisteredException {
        if (!userRepository.existsByEmail(email)) {
            return;
        }
        String msg = String.format(USER_ALREADY_REGISTERED_MSG, email);
        log.error(msg);
        throw new UserAlreadyRegisteredException(msg);
    }

    private String getOAuthRole(AccountType accountType) {
        return switch (accountType) {
            case REFUGEE -> AuthRoles.REFUGEE_ROLE;
            case HOST -> AuthRoles.HOST_ROLE;
        };
    }

    private boolean shouldPasswordBeModified(UserModel userModel, UserEntity userEntity) {
        return !passwordEncoder.matches(userModel.getPassword(), userEntity.getHashedPassword());
    }
}
