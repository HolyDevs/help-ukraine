package help.ukraine.app.unit.service;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
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
import static org.mockito.Mockito.doReturn;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
class UserServiceTest {
    private static final String EXISTING_EMAIL = "jan.testowy@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "aaa.bbb@ccc.com";

    @MockBean
    private UserRepository userRepository;

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
                .hashedPassword("$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.") // hashed "aaa"
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
        doReturn(Optional.of(userEntity)).when(userRepository).findByEmail(EXISTING_EMAIL);
        doReturn(Optional.empty()).when(userRepository).findByEmail(NOT_EXISTING_EMAIL);
    }

    @Transactional
    @Test
    void fetchUserOkTest() throws Exception {
        UserModel userModel = userService.getUserByEmail(EXISTING_EMAIL);
        assertEquals(EXISTING_EMAIL, userModel.getEmail());
    }

    @Transactional
    @Test
    void fetchUserNotFoundTest() {
        assertThrows(DataNotExistsException.class, () -> userService.getUserByEmail(NOT_EXISTING_EMAIL));
    }
}
