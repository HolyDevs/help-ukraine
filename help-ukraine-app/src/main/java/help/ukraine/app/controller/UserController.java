package help.ukraine.app.controller;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.exception.UserNoAccessException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {

    // ENDPOINTS
    public static final String USER_ENDPOINT = "/user";
    // PARAMS
    public static final String EMAIL_PARAM_NAME = "email";
    // MESSAGES
    private static final String PARAM_AND_BODY_EMAILS_NOT_MATCH = "Email passed as parameter (%s) and one placed in body (%s) do not match";

    private final UserService userService;

    @GetMapping("/hello")
    public String get() {
        log.debug("hello endpoint hit");
        return "<h2>Hello Ukraine</<h2>";
    }

    // CRUD ENDPOINTS

    @GetMapping(value = AuthUrls.BACKEND_URL + USER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUser(@RequestParam(EMAIL_PARAM_NAME) String email) throws UserNoAccessException, DataNotExistsException {
        log.debug("fetch user endpoint hit");
        UserModel userModel = userService.fetchUser(email);
        return ResponseEntity.ok().body(userModel);
    }

    @PutMapping(value = AuthUrls.BACKEND_URL + USER_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> modifyUser(@RequestParam(EMAIL_PARAM_NAME) String email, @Valid @RequestBody UserModel userModel) throws UserNoAccessException, DataNotExistsException, UserAlreadyRegisteredException {
        log.debug("modify user endpoint hit");
        badRequestIfParamAndBodyEmailsNotMatch(email, userModel);
        if (userService.existsUser(email)) {
            UserModel modifiedUserModel = userService.updateUser(userModel);
            return ResponseEntity.ok().body(modifiedUserModel);
        }
        UserModel registeredUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserModel);
    }

    @PostMapping(value = AuthUrls.BACKEND_URL + USER_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel) throws UserAlreadyRegisteredException {
        log.debug("register user endpoint hit");
        UserModel registeredUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserModel);
    }

    @DeleteMapping(AuthUrls.BACKEND_URL + USER_ENDPOINT)
    public ResponseEntity<Void> deleteUser(@RequestParam(EMAIL_PARAM_NAME) String email) throws UserNoAccessException, DataNotExistsException {
        log.debug("delete user endpoint hit");
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(DataNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDataNotExistsException(DataNotExistsException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyRegisteredException(UserAlreadyRegisteredException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNoAccessException(UserNoAccessException exception) {
        return exception.getMessage();
    }

    private void badRequestIfParamAndBodyEmailsNotMatch(String emailParam, UserModel userModel) {
        if (emailParam.equals(userModel.getEmail())) {
            return;
        }
        String msg = String.format(PARAM_AND_BODY_EMAILS_NOT_MATCH, emailParam, userModel.getEmail());
        log.error(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

}