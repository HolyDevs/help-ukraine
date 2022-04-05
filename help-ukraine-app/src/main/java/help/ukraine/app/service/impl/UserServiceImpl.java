package help.ukraine.app.service.impl;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.security.constants.SecurityConstants;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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

    private final MapperFacade userMapperFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserModel userModel = getUserByEmail(username);
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
    public UserModel getUserByEmail(String email) throws DataNotExistsException {
        Optional<UserEntity> optional = userRepository.findByEmail(email);
        throwIfMissingUser(optional, email);
        log.info(String.format(FETCHED_USER_MSG, email));
        return userMapperFacade.map(optional.get(), UserModel.class);
    }

    public UserModel saveUser(UserModel userModel) {
        String password = userModel.getPassword();
        userModel.setPassword(passwordEncoder.encode(password));
        UserEntity userEntity = userMapperFacade.map(userModel, UserEntity.class);
        return userMapperFacade.map(userRepository.save(userEntity), UserModel.class);
    }

    private void throwIfMissingUser(Optional<UserEntity> optional, String email) throws DataNotExistsException {
        if (optional.isPresent()) {
            return;
        }
        String msg = String.format(MISSING_USER_MSG, email);
        log.error(msg);
        throw new DataNotExistsException(msg);
    }

    private String getOAuthRole(AccountType accountType) {
        return switch (accountType) {
            case REFUGEE -> SecurityConstants.REFUGEE_ROLE;
            case HOST -> SecurityConstants.HOST_ROLE;
        };
    }
}
