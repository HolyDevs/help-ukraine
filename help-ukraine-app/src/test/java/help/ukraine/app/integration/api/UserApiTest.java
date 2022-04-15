package help.ukraine.app.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import help.ukraine.app.controller.UserController;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.UserRepository;
import help.ukraine.app.security.constants.AuthUrls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static help.ukraine.app.controller.UserController.EMAIL_PARAM_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "integrationtest")
class UserApiTest {

    // ENDPOINTS
    private static final String USER_ENDPOINT = "/user";
    // EMAILS
    private static final String EXISTING_EMAIL = "jan.testowy@gmail.com";
    private static final String REGISTERED_EMAIL = "jan.testowy1@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "aaa.bbb@ccc.com";
    // PAYLOADS PATHS
    private static final String USER_REGISTER_PAYLOAD_PATH = "payloads/users/userRegisterPayload.json";
    private static final String USER_REGISTER_PAYLOAD_NULL_NAME_PATH = "payloads/users/userRegisterPayloadNullName.json";
    // TOKENS - VALID TOKENS ARE GENERATED FOR LOCAL SECRET 'SECRET' AND ARE VALID FOR 60 YRS
    private static final String VALID_AUTH_HEADER_EXISTING_EMAIL_REFUGEE_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9SRUZVR0VFIiwiaXNzIjoiaXNzdWVyIiwiZXhwIjozNTQxMzY1OTMzfQ.S7nqWrLC13dQ-Pdl_SX0HZi-8-95pGAv4FaVDSwEHfw";
    private static final String VALID_AUTH_HEADER_EXISTING_EMAIL_NO_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJpc3MiOiJpc3N1ZXIiLCJleHAiOjM1NDEzNjU5ODV9.2yOeL-UHYn-nQu9gjqxiS5v2WG43WJiDI-Lbf1VhCTU";
    private static final String VALID_AUTH_HEADER_NO_EXISTING_EMAIL_REFUGEE_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWEuYmJiQGNjYy5jb20iLCJyb2xlIjoiUk9MRV9SRUZVR0VFIiwiaXNzIjoiaXNzdWVyIiwiZXhwIjozNTQxMzY1ODY2fQ.qPvmMiltCRYPIg9sAdbwqHBhJz1NGWdJvn-3B4x-cQo";
    private static final String NOT_VALID_AUTH_HEADER = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJyb2xlIjoiUmVmdWdlZSIsImlzcyI6Imlzc3VlciIsImV4cCI6NDgwMjQzNDY5OX0.Gwh34iQtUzO1a1uKK";

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
        MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?" + EMAIL_PARAM_NAME + "=" + EXISTING_EMAIL)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_EMAIL_REFUGEE_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
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
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?" + EMAIL_PARAM_NAME + "=" + NOT_EXISTING_EMAIL)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NO_EXISTING_EMAIL_REFUGEE_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void fetchUserNotValidTokenForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?" + EMAIL_PARAM_NAME + "=" + NOT_EXISTING_EMAIL)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, NOT_VALID_AUTH_HEADER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void fetchUserTokenWithNoRoleForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "?" + EMAIL_PARAM_NAME + "=" + EXISTING_EMAIL)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_EMAIL_NO_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void registerUserCreatedAndBadRequestTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_REGISTER_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // POST - CREATED
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(AuthUrls.REGISTER_USER_URL)
                        .servletPath(AuthUrls.REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String postContent = mvcPostResult.getResponse().getContentAsString();
        UserModel registeredUserModel = objectMapper.readValue(postContent, UserModel.class);
        assertEquals(REGISTERED_EMAIL, registeredUserModel.getEmail());
        assertEquals(AccountType.REFUGEE, registeredUserModel.getAccountType());

        // POST - BAD REQUEST
        mvc.perform(MockMvcRequestBuilders.post(AuthUrls.REGISTER_USER_URL)
                        .servletPath(AuthUrls.REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void registerUserValidationBadRequestTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_REGISTER_PAYLOAD_NULL_NAME_PATH), StandardCharsets.UTF_8);

        // POST - BAD REQUEST
        mvc.perform(MockMvcRequestBuilders.post(AuthUrls.REGISTER_USER_URL)
                        .servletPath(AuthUrls.REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isBadRequest());
    }

}
