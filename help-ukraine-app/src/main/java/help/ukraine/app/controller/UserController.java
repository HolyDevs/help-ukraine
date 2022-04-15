package help.ukraine.app.controller;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.exception.UserAlreadyRegisteredException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.AuthChecker;
import help.ukraine.app.security.constants.AuthMessages;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
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
public class UserController {

    public static final String EMAIL_PARAM_NAME = "email";
    private static final String PARAM_AND_BODY_EMAILS_NOT_MATCH = "Email passed as parameter (%s) and one placed in body (%s) do not match";

    private final UserService userService;
    private final AuthChecker authChecker;

    @GetMapping("/hello")
    public String get() {
        log.debug("hello endpoint hit");
        return "<h2>Hello Ukraine</<h2>";
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<UserModel> getUser(@RequestParam(EMAIL_PARAM_NAME) String email) {
        log.debug("fetch user endpoint hit");
        throwIfAuthNotBelongsToUser(email);
        try {
            UserModel userModel = userService.getUser(email);
            return ResponseEntity.ok().body(userModel);
        } catch (DataNotExistsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping(value = "/user/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<UserModel> modifyUser(@RequestParam(EMAIL_PARAM_NAME) String email, @Valid @RequestBody UserModel userModel) {
        log.debug("modify user endpoint hit");
        throwIfParamAndBodyEmailsNotMatch(email, userModel);
        throwIfAuthNotBelongsToUser(email);
        UserModel modifiedUserModel = userService.modifyUser(userModel);
        return ResponseEntity.ok().body(modifiedUserModel);
    }

    @PostMapping(value = AuthUrls.REGISTER_USER_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel) {
        try {
            log.debug("register user endpoint hit");
            UserModel registeredUserModel = userService.registerUser(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserModel);
        } catch (UserAlreadyRegisteredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam(EMAIL_PARAM_NAME) String email) {
        log.debug("delete user endpoint hit");
        throwIfAuthNotBelongsToUser(email);
        try {
            userService.deleteUser(email);
            return ResponseEntity.noContent().build();
        } catch (DataNotExistsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private void throwIfAuthNotBelongsToUser(String email) {
        if (authChecker.checkIfAuthBelongsToUser(email)) {
            return;
        }
        log.error(AuthMessages.USER_NO_ACCESS_MSG);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, AuthMessages.USER_NO_ACCESS_MSG);
    }

    private void throwIfParamAndBodyEmailsNotMatch(String emailParam, UserModel userModel) {
        if (emailParam.equals(userModel.getEmail())) {
            return;
        }
        String msg = String.format(PARAM_AND_BODY_EMAILS_NOT_MATCH, emailParam, userModel.getEmail());
        log.error(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

}