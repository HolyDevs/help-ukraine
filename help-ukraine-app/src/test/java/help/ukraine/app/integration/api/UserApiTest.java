package help.ukraine.app.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import help.ukraine.app.data.RefugeeEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static help.ukraine.app.controller.UserController.USER_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "integrationtest")
class UserApiTest {

    // IDS
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long REGISTERED_USER_ID = 2L;
    private static final Long NOT_EXISTING_USER_ID = 100L;
    // EMAILS
    private static final String EXISTING_USER_EMAIL = "jan.testowy@gmail.com";
    private static final String REGISTERED_USER_EMAIL = "jan.testowy1@gmail.com";
    private static final String NOT_EXISTING_USER_EMAIL = "aaa.bbb@ccc.com";
    // NAMES
    private static final String NAME = "Jan";
    private static final String MODIFIED_NAME = "Andrzej";
    // PASSWORDS
    private static final String PASSWORD = "aaa";
    private static final String MODIFIED_PASSWORD = "bbb";
    // REGISTER PAYLOADS PATHS
    private static final String USER_REGISTER_PAYLOAD_PATH = "payloads/users/register/userRegisterPayload.json";
    private static final String USER_REGISTER_PAYLOAD_NULL_NAME_PATH = "payloads/users/register/userRegisterPayloadNullName.json";
    // MODIFY PAYLOADS PATHS
    private static final String USER_MODIFY_NAME_PAYLOAD_PATH = "payloads/users/modify/userModifyNamePayload.json";
    private static final String USER_MODIFY_NAME_PAYLOAD_NOT_EXISTING_EMAIL_PATH = "payloads/users/modify/userModifyNamePayloadNotExistingEmail.json";
    private static final String USER_MODIFY_PASSWORD_PAYLOAD_PATH = "payloads/users/modify/userModifyPasswordPayload.json";
    // TOKENS - VALID TOKENS ARE GENERATED FOR LOCAL SECRET 'SECRET' AND ARE VALID FOR 100 YRS
    private static final String VALID_AUTH_HEADER_EXISTING_USER_REFUGEE_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJ1c2VyX3JvbGUiOiJST0xFX1JFRlVHRUUiLCJ1c2VyX2lkIjoxLCJpc3MiOiJpc3N1ZXIiLCJleHAiOjQ4MDg2NDI0NTZ9.tj5jxN8HD4hoUCuWqirvApzwW5le1NkP4VsjIlmz6Qs";
    private static final String VALID_AUTH_HEADER_EXISTING_USER_NO_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJ1c2VyX2lkIjoxLCJpc3MiOiJpc3N1ZXIiLCJleHAiOjQ4MDg2NDI1NDh9.bPF_4q2phSjF5cA4Yf8AO1dcIyz8AMcvdxPelnXWP90";
    private static final String VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWEuYmJiQGNjYy5jb20iLCJ1c2VyX3JvbGUiOiJST0xFX1JFRlVHRUUiLCJ1c2VyX2lkIjoxMDAsImlzcyI6Imlzc3VlciIsImV4cCI6NDgwODY0MzE1Mn0.BN8pMUUVB3ZOriWw5kTkNuFdPrHX_nuVGPHy5VNFR88";
    private static final String NOT_VALID_AUTH_HEADER = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW4udGVzdG93eUBnbWFpbC5jb20iLCJyb2xlIjoiUmVmdWdlZSIsImlzcyI6Imlzc3VlciIsImV4cCI6NDgwMjQzNDY5OX0.Gwh34iQtUzO1a1uKK";


    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefugeeRepository refugeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void saveUser() {
        entityManager
                .createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1")
                .executeUpdate();
        UserEntity userEntity = UserEntity.builder()
                .email(EXISTING_USER_EMAIL)
                .name("Jan")
                .surname("Testowy")
                .accountType(AccountType.REFUGEE)
                .birthDate(LocalDate.now())
                .hashedPassword("$2a$10$.2hoSJVTOkQAbU1BLy09Y.LycOAOjb3513D9ON6Q/gUjuT8GShZa.") // hashed "aaa"
                .isAccountVerified(true)
                .phoneNumber("666-666-666")
                .sex(Sex.MALE)
                .build();
        userEntity = userRepository.save(userEntity);
        RefugeeEntity refugeeEntity = RefugeeEntity.builder().userId(userEntity.getId()).build();
        refugeeRepository.save(refugeeEntity);
    }

    @Transactional
    @Test
    void fetchUserOkTest() throws Exception {
        // GET - OK
        MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/"  + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT + "/"  + EXISTING_USER_ID)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_REFUGEE_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String body = mvcGetResult.getResponse().getContentAsString();

        UserModel userModel = objectMapper.readValue(body, UserModel.class);
        assertEquals(EXISTING_USER_EMAIL, userModel.getEmail());
        assertEquals(EXISTING_USER_ID, userModel.getId());
        assertEquals(NAME, userModel.getName());
    }

    @Transactional
    @Test
    void fetchUserNotFoundTest() throws Exception {
        // GET - NOT FOUND
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void fetchUserNotValidTokenForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, NOT_VALID_AUTH_HEADER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void fetchUserTokenWithNoRoleForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_NO_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void fetchUserTokenForWrongUserForbiddenTest() throws Exception {
        // GET - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void registerUserCreatedAndBadRequestTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_REGISTER_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // POST - CREATED
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                        .servletPath(USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String postContent = mvcPostResult.getResponse().getContentAsString();
        UserModel registeredUserModel = objectMapper.readValue(postContent, UserModel.class);
        assertEquals(REGISTERED_USER_EMAIL, registeredUserModel.getEmail());
        assertEquals(AccountType.REFUGEE, registeredUserModel.getAccountType());
        assertEquals(NAME, registeredUserModel.getName());
        assertTrue(passwordEncoder.matches(PASSWORD, registeredUserModel.getPassword()));

        // POST - BAD REQUEST
        mvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                        .servletPath(USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void registerUserValidationBadRequestTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_REGISTER_PAYLOAD_NULL_NAME_PATH), StandardCharsets.UTF_8);

        // POST - BAD REQUEST
        mvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                        .servletPath(USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void deleteUserNoContentTest() throws Exception {
        // DELETE - NO CONTENT
        mvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_REFUGEE_ROLE))
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    void deleteUserNotFoundTest() throws Exception {
        // DELETE - NOT FOUND
        mvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void deleteUserNotValidTokenForbiddenTest() throws Exception {
        // DELETE - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, NOT_VALID_AUTH_HEADER))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void deleteUserTokenWithNoRoleForbiddenTest() throws Exception {
        // DELETE - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_NO_ROLE))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void deleteUserTokenForWrongUserForbiddenTest() throws Exception {
        // DELETE - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void modifyUserNameTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // PUT - OK
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_REFUGEE_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isOk())
                .andReturn();

        String postContent = mvcPostResult.getResponse().getContentAsString();
        UserModel modifiedUserModel = objectMapper.readValue(postContent, UserModel.class);
        assertEquals(EXISTING_USER_EMAIL, modifiedUserModel.getEmail());
        assertEquals(AccountType.REFUGEE, modifiedUserModel.getAccountType());
        assertEquals(MODIFIED_NAME, modifiedUserModel.getName());
        assertTrue(passwordEncoder.matches(PASSWORD, modifiedUserModel.getPassword()));
    }

    @Transactional
    @Test
    void modifyUserNameNotFoundTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_NOT_EXISTING_EMAIL_PATH), StandardCharsets.UTF_8);

        // PUT - NOT FOUND
        mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + NOT_EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void modifyUserNameNotValidTokenForbiddenTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // PUT - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, NOT_VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void modifyUserNameIdAndBodyIdNotMatchBadRequestTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_NOT_EXISTING_EMAIL_PATH), StandardCharsets.UTF_8);

        // PUT - BAD REQUEST
        mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isBadRequest());
    }


    @Transactional
    @Test
    void modifyUserNameTokenWithNoRoleForbiddenTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // PUT - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_NO_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    void modifyUserNameTokenForWrongUserForbiddenTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_NAME_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // PUT - FORBIDDEN
        mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_NOT_EXISTING_USER_REFUGEE_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isForbidden());
    }


    @Transactional
    @Test
    void modifyUserPasswordTest() throws Exception {
        String userUploadPayload = Resources.toString(Resources.getResource(USER_MODIFY_PASSWORD_PAYLOAD_PATH), StandardCharsets.UTF_8);

        // PUT - OK
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/" + EXISTING_USER_ID)
                        .servletPath(USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, VALID_AUTH_HEADER_EXISTING_USER_REFUGEE_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userUploadPayload))
                .andExpect(status().isOk())
                .andReturn();

        String postContent = mvcPostResult.getResponse().getContentAsString();
        UserModel modifiedUserModel = objectMapper.readValue(postContent, UserModel.class);
        assertEquals(EXISTING_USER_EMAIL, modifiedUserModel.getEmail());
        assertEquals(AccountType.REFUGEE, modifiedUserModel.getAccountType());
        assertEquals(NAME, modifiedUserModel.getName());
        assertTrue(passwordEncoder.matches(MODIFIED_PASSWORD, modifiedUserModel.getPassword()));
    }
}
