package help.ukraine.app.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "integrationtest")
class AppApplicationTest {

    private static final String USER_ENDPOINT = "/user";
    private static final String EXISTING_EMAIL = "jan.testowy@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "aaa.bbb@ccc.com";
    private static final String NOT_VALID_AUTH_HEADER = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJyb2xlIjoiUmVmdWdlZSIsImlzcyI6Imlzc3VlciIsImV4cCI6NDgwMjQzNDY5OX0.Gwh34iQtUzO1a1uKK";
    // TOKEN GENERATED FOR LOCAL SECRET 'SECRET', VALID FOR 100 YRS
    private static final String VALID_AUTH_HEADER = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJyb2xlIjoiUmVmdWdlZSIsImlzcyI6Imlzc3VlciIsImV4cCI6NDgwMjQzNDY5OX0.Gwh34iQtUzO1a1uKKxE2oj4HcjG1D8ZS_MErzEEmI-M";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void saveUser() {
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
        userRepository.save(userEntity);
    }

    @Transactional
    @Test
    void fetchUserOkTest() throws Exception {
        // GET - OK
        MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?email=" + EXISTING_EMAIL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isOk())
                .andReturn();
        String body = mvcGetResult.getResponse().getContentAsString();

        UserModel userModel = objectMapper.readValue(body, UserModel.class);
        assertEquals(EXISTING_EMAIL, userModel.getEmail());
    }

    @Transactional
    @Test
    void fetchUserNotFoundTest() throws Exception {
        // GET - NOT FOUND
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?email=" + NOT_EXISTING_EMAIL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void fetchUserForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?email=" + NOT_EXISTING_EMAIL)
                        .header("Authorization", NOT_VALID_AUTH_HEADER))
                .andExpect(status().isForbidden());
    }

}
