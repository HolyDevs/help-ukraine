package help.ukraine.app.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import help.ukraine.app.data.UserEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "integrationtest")
class AppApplicationTest {

    private static final String USER_ENDPOINT = "/user";
    private static final String CREATED_USER_ID = "666";
    private static final String NOT_EXISTING_USER_ID = "667";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    

    @Transactional
    @Test
    void fetchUserOkTest() throws Exception {
        // GET - OK
        MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + CREATED_USER_ID))
                .andExpect(status().isOk())
                .andReturn();
        String body = mvcGetResult.getResponse().getContentAsString();

        UserModel userModel = objectMapper.readValue(body, UserModel.class);
        assertEquals(CREATED_USER_ID, userModel.getId());
    }

    @Transactional
    @Test
    void fetchUserNotFoundTest() throws Exception {
        // GET - NOT FOUND
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID))
                .andExpect(status().isNotFound());
    }

}
