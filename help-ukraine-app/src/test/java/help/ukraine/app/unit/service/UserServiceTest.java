package help.ukraine.app.unit.service;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
class UserServiceTest {
    private static final String EXISTING_USER_ID = "666";
    private static final String NOT_EXISTING_USER_ID = "667";

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUpMock() {
//        UserEntity userEntity = new UserEntity(EXISTING_USER_ID, "Jan", "Testowy", "aaa");
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(EXISTING_USER_ID);
//        doReturn(Optional.empty()).when(userRepository).findById(NOT_EXISTING_USER_ID);
    }

    @Test
    void fetchUserOkTest() throws Exception {
        UserModel userModel = userService.getUser(EXISTING_USER_ID);
        assertEquals(EXISTING_USER_ID, userModel.getId());
    }

    @Test
    void fetchUserNotFoundTest() {
        assertThrows(DataNotExistsException.class, () -> userService.getUser(NOT_EXISTING_USER_ID));
    }
}
