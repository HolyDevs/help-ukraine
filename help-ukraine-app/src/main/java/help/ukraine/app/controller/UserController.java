package help.ukraine.app.controller;

import help.ukraine.app.exception.UserEmailNotUniqueException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(UserController.USER_ENDPOINT)
public class UserController {

    // ENDPOINTS
    public static final String USER_ENDPOINT = AuthUrls.BACKEND_ROOT + "/user";

    // MESSAGES
    private static final String PARAM_AND_BODY_IDS_NOT_MATCH = "Id passed as parameter (%d) and one placed in body (%d) do not match";

    private final UserService userService;
    private final AuthService authService;

    // CRUD ENDPOINTS

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) throws UserNoAccessException, UserNotExistsException {
        log.debug("fetch user endpoint hit");
        authService.throwIfAuthNotBelongToUser(id);
        UserModel userModel = userService.getUserById(id);
        return ResponseEntity.ok().body(userModel);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<UserModel> modifyUser(@PathVariable Long id, @Valid @RequestBody UserModel userModel) throws UserNoAccessException, UserNotExistsException, UserEmailNotUniqueException {
        log.debug("modify user endpoint hit");
        badRequestIfParamAndBodyIdsNotMatch(id, userModel);
        authService.throwIfAuthNotBelongToUser(id);
        UserModel modifiedUserModel = userService.updateUser(userModel);
        return ResponseEntity.ok().body(modifiedUserModel);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel) throws UserEmailNotUniqueException {
        log.debug("register user endpoint hit");
        UserModel registeredUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserModel);
    }

    @DeleteMapping("/{id}")
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNoAccessException, UserNotExistsException {
        log.debug("delete user endpoint hit");
        authService.throwIfAuthNotBelongToUser(id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserEmailNotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleBadRequestExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenExceptions(Exception exception) {
        return exception.getMessage();
    }

    private void badRequestIfParamAndBodyIdsNotMatch(Long id, UserModel userModel) {
        if (id.equals(userModel.getId())) {
            return;
        }
        String msg = String.format(PARAM_AND_BODY_IDS_NOT_MATCH, id, userModel.getId());
        log.error(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
}