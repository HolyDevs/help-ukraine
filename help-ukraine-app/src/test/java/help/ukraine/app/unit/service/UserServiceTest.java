package help.ukraine.app.unit.service;

import help.ukraine.app.data.RefugeeEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.HostRepository;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
class UserServiceTest {

    // EMAILS
    private static final String EXISTING_EMAIL = "jan.testowy@gmail.com";
    private static final String REGISTERED_EMAIL = "jan.testowy@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "aaa.bbb@ccc.com";
    // PASSWORDS
    private static final String PASSWORD = "aaa";
    private static final String HASHED_PASSWORD = "$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa."; // hashed "aaa"

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RefugeeRepository refugeeRepository;

    @MockBean
    private HostRepository hostRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUpMock() {
        UserEntity userEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .name("Jan")
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .hashedPassword(HASHED_PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
        doReturn(Optional.of(userEntity)).when(userRepository).findByEmail(EXISTING_EMAIL);
        doReturn(true).when(userRepository).existsByEmail(EXISTING_EMAIL);
        doReturn(Optional.empty()).when(userRepository).findByEmail(NOT_EXISTING_EMAIL);
        doReturn(false).when(userRepository).existsByEmail(NOT_EXISTING_EMAIL);
    }

    @Transactional
    @Test
    void fetchUserOkTest() throws Exception {
        UserModel userModel = userService.getUserByEmail(EXISTING_EMAIL);
        assertEquals(EXISTING_EMAIL, userModel.getEmail());
    }

    @Transactional
    @Test
    void fetchUserDataNotExistsExceptionTest() {
        assertThrows(DataNotExistsException.class, () -> userService.getUserByEmail(NOT_EXISTING_EMAIL));
    }

    @Transactional
    @Test
    void registerUserCreatedTest() throws Exception {
        // MOCKS SETUP
        UserEntity registeredUserEntity = buildRegisteredUserEntity();
        doReturn(registeredUserEntity).when(userRepository).save(any());
        doReturn(false).when(userRepository).existsByEmail(REGISTERED_EMAIL);
        RefugeeEntity registeredRefugeeEntity = buildRegisteredRefugeeEntity(registeredUserEntity);
        doReturn(registeredRefugeeEntity).when(refugeeRepository).save(any());

        // USER REGISTRATION ASSERTION
        UserModel userModelToRegister = buildUserModelToRegister();
        UserModel registeredUserModel = userService.registerUser(userModelToRegister);
        assertEquals(userModelToRegister.getEmail(), registeredUserModel.getEmail());
        assertEquals(userModelToRegister.getAccountType(), registeredUserModel.getAccountType());
        assertEquals(userModelToRegister.getSurname(), registeredUserModel.getSurname());
        assertEquals(HASHED_PASSWORD, registeredUserModel.getPassword());
    }

    @Transactional
    @Test
    void registerUserAlreadyRegisteredExceptionTest() {
        // MOCKS SETUP
        doReturn(true).when(userRepository).existsByEmail(REGISTERED_EMAIL);

        // USER REGISTRATION ASSERTION
        UserModel userModelToRegister = buildUserModelToRegister();
        assertThrows(UserAlreadyRegisteredException.class, () -> userService.registerUser(userModelToRegister));
    }

    private RefugeeEntity buildRegisteredRefugeeEntity(UserEntity userEntity) {
        return RefugeeEntity.builder()
                .userId(userEntity.getId())
                .user(userEntity)
                .build();
    }

    private UserEntity buildRegisteredUserEntity() {
        return UserEntity.builder()
                .id(100L)
                .email(REGISTERED_EMAIL)
                .name("Jan")
                .surname("Testowy1")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .hashedPassword(HASHED_PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }

    private UserModel buildUserModelToRegister() {
        return UserModel.builder()
                .email(REGISTERED_EMAIL)
                .name("Jan")
                .surname("Testowy1")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .password(PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }
}
