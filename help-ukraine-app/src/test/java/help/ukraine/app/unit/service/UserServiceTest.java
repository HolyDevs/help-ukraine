package help.ukraine.app.unit.service;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
class UserServiceTest {
    private static final String EXISTING_USERNAME = "666";
    private static final String NOT_EXISTING_USERNAME = "667";

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUpMock() {
//        UserEntity userEntity = new UserEntity(EXISTING_USERNAME, "Jan", "Testowy", "aaa");
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(EXISTING_USERNAME);
        doReturn(Optional.empty()).when(userRepository).findById(NOT_EXISTING_USERNAME);
    }

    @Transactional
    @Test
    @Disabled
    void fetchUserOkTest() throws Exception {
        UserModel userModel = userService.getUser(EXISTING_USERNAME);
        assertEquals(EXISTING_USERNAME, userModel.getUsername());
    }

    @Transactional
    @Test
    void fetchUserNotFoundTest() {
        assertThrows(DataNotExistsException.class, () -> userService.getUser(NOT_EXISTING_USERNAME));
    }
}
