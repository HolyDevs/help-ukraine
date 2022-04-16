package help.ukraine.app.unit.service;

import help.ukraine.app.data.RefugeeEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.exception.UserNoAccessException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.HostRepository;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
class UserServiceTest {

    // IDS
    private static final Long EXISTING_ID = 1L;
    private static final Long REGISTERED_ID = 100L;
    // EMAILS
    private static final String EXISTING_EMAIL = "jan.testowy@gmail.com";
    private static final String REGISTERED_EMAIL = "jan.testowy1@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "aaa.bbb@ccc.com";
    // NAMES
    private static final String NAME = "Jan";
    private static final String MODIFIED_NAME = "Andrzej";
    // PASSWORDS
    private static final String PASSWORD = "aaa";
    private static final String HASHED_PASSWORD = "$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa."; // hashed "aaa"
    private static final String MODIFIED_PASSWORD = "bbb";
    private static final String HASHED_MODIFIED_PASSWORD = "$2a$10$1O6ru7a.cdzt4F88tvNWautFeNBoTrzXNSRHPGqjj9CTu4Dm50L62"; // hashed "bbb"

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
                .id(EXISTING_ID)
                .email(EXISTING_EMAIL)
                .name(NAME)
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
    @WithMockUser(username = EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void fetchUserOkTest() throws Exception {
        UserModel userModel = userService.fetchUser(EXISTING_EMAIL);
        assertEquals(EXISTING_EMAIL, userModel.getEmail());
    }

    @Transactional
    @WithMockUser(username = NOT_EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void fetchUserDataNotExistsExceptionTest() {
        assertThrows(DataNotExistsException.class, () -> userService.fetchUser(NOT_EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = NOT_EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void fetchUserUserNoAccessExceptionTest() {
        assertThrows(UserNoAccessException.class, () -> userService.fetchUser(EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL)
    @Test
    void fetchUserAccessDeniedExceptionTest() {
        assertThrows(AccessDeniedException.class, () -> userService.fetchUser(EXISTING_EMAIL));
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
        UserModel registeredUserModel = userService.createUser(userModelToRegister);
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
        assertThrows(UserAlreadyRegisteredException.class, () -> userService.createUser(userModelToRegister));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void deleteUserTest() {
        assertDoesNotThrow(() -> userService.deleteUser(EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = NOT_EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void deleteUserDataNotExistsExceptionTest() {
        assertThrows(DataNotExistsException.class, () -> userService.deleteUser(NOT_EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = NOT_EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void deleteUserUserNoAccessExceptionTest() {
        assertThrows(UserNoAccessException.class, () -> userService.deleteUser(EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL)
    @Test
    void deleteUserAccessDeniedExceptionTest() {
        assertThrows(AccessDeniedException.class, () -> userService.deleteUser(EXISTING_EMAIL));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void modifyUserNameTest() throws Exception {
        // MOCKS SETUP
        UserEntity modifiedUserEntity = buildModifiedExistingUserEntityWithModifiedName();
        doReturn(modifiedUserEntity).when(userRepository).save(any());

        // USER MODIFICATION ASSERTIONS
        UserModel userModelToModify = buildExistingUserModelWithModifiedName();
        UserModel modifiedUserModel = userService.updateUser(userModelToModify);
        assertEquals(MODIFIED_NAME, modifiedUserModel.getName());
        assertEquals(EXISTING_EMAIL, modifiedUserModel.getEmail());
    }

    @Transactional
    @WithMockUser(username = NOT_EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void modifyUserNameUserNoAccessExceptionTest() {
        // MOCKS SETUP
        UserEntity modifiedUserEntity = buildModifiedExistingUserEntityWithModifiedName();
        doReturn(modifiedUserEntity).when(userRepository).save(any());

        // USER MODIFICATION ASSERTIONS
        UserModel userModelToModify = buildExistingUserModelWithModifiedName();
        assertThrows(UserNoAccessException.class, () -> userService.updateUser(userModelToModify));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL)
    @Test
    void modifyUserNameAccessDeniedExceptionTest() {
        // MOCKS SETUP
        UserEntity modifiedUserEntity = buildModifiedExistingUserEntityWithModifiedName();
        doReturn(modifiedUserEntity).when(userRepository).save(any());

        // USER MODIFICATION ASSERTIONS
        UserModel userModelToModify = buildExistingUserModelWithModifiedName();
        assertThrows(AccessDeniedException.class, () -> userService.updateUser(userModelToModify));
    }

    @Transactional
    @WithMockUser(username = EXISTING_EMAIL, authorities = AuthRoles.REFUGEE_ROLE)
    @Test
    void modifyUserPasswordTest() throws Exception {
        // MOCKS SETUP
        UserEntity modifiedUserEntity = buildModifiedExistingUserEntityWithModifiedPassword();
        doReturn(modifiedUserEntity).when(userRepository).save(any());

        // USER MODIFICATION ASSERTIONS
        UserModel userModelToModify = buildExistingUserModelWithModifiedPassword();
        UserModel modifiedUserModel = userService.updateUser(userModelToModify);
        assertEquals(HASHED_MODIFIED_PASSWORD, modifiedUserModel.getPassword());
        assertEquals(EXISTING_EMAIL, modifiedUserModel.getEmail());
    }

    private RefugeeEntity buildRegisteredRefugeeEntity(UserEntity userEntity) {
        return RefugeeEntity.builder()
                .userId(userEntity.getId())
                .user(userEntity)
                .build();
    }

    private UserEntity buildRegisteredUserEntity() {
        return UserEntity.builder()
                .id(REGISTERED_ID)
                .email(REGISTERED_EMAIL)
                .name(NAME)
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
                .name(NAME)
                .surname("Testowy1")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .password(PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }

    private UserModel buildExistingUserModelWithModifiedName() {
        return UserModel.builder()
                .email(EXISTING_EMAIL)
                .name(MODIFIED_NAME)
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .password(PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }

    private UserEntity buildModifiedExistingUserEntityWithModifiedName() {
        return UserEntity.builder()
                .id(EXISTING_ID)
                .email(EXISTING_EMAIL)
                .name(MODIFIED_NAME)
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .hashedPassword(HASHED_PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }

    private UserModel buildExistingUserModelWithModifiedPassword() {
        return UserModel.builder()
                .email(EXISTING_EMAIL)
                .name(NAME)
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .password(MODIFIED_PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }

    private UserEntity buildModifiedExistingUserEntityWithModifiedPassword() {
        return UserEntity.builder()
                .id(EXISTING_ID)
                .email(EXISTING_EMAIL)
                .name(NAME)
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(new Date())
                .hashedPassword(HASHED_MODIFIED_PASSWORD)
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
    }
}
