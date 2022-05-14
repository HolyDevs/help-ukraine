package help.ukraine.app.controller;

import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.exception.UserEmailNotUniqueException;
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

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(UserController.USER_ENDPOINT)
public class UserController {

    // ENDPOINTS
    public static final String USER_ENDPOINT = AuthUrls.BACKEND_ROOT + "/user";
    // PARAMS
    public static final String EMAIL_PARAM_NAME = "email";
    // MESSAGES
    private static final String PARAM_AND_BODY_EMAILS_NOT_MATCH = "Email passed as parameter (%s) and one placed in body (%s) do not match";

    private final UserService userService;

    // CRUD ENDPOINTS

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUser(@RequestParam(EMAIL_PARAM_NAME) String email) throws UserNoAccessException, UserNotExistsException {
        log.debug("fetch user endpoint hit");
        UserModel userModel = userService.fetchUserByEmail(email);
        return ResponseEntity.ok().body(userModel);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> modifyUser(@RequestParam(EMAIL_PARAM_NAME) String email, @Valid @RequestBody UserModel userModel) throws UserNoAccessException, UserNotExistsException, UserEmailNotUniqueException {
        log.debug("modify user endpoint hit");
//        badRequestIfParamAndBodyEmailsNotMatch(email, userModel);
        UserModel modifiedUserModel = userService.updateUser(email, userModel);
        return ResponseEntity.ok().body(modifiedUserModel);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel) throws UserEmailNotUniqueException {
        log.debug("register user endpoint hit");
        UserModel registeredUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserModel);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam(EMAIL_PARAM_NAME) String email) throws UserNoAccessException, UserNotExistsException {
        log.debug("delete user endpoint hit");
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotExistsException(UserNotExistsException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserEmailNotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyRegisteredException(UserEmailNotUniqueException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNoAccessException(UserNoAccessException exception) {
        return exception.getMessage();
    }

//    private void badRequestIfParamAndBodyEmailsNotMatch(String emailParam, UserModel userModel) {
//        if (emailParam.equals(userModel.getEmail())) {
//            return;
//        }
//        String msg = String.format(PARAM_AND_BODY_EMAILS_NOT_MATCH, emailParam, userModel.getEmail());
//        log.error(msg);
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
//    }

}