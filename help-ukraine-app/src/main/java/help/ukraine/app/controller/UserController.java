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

    private final UserService userService;
    private final AuthChecker authChecker;

    @GetMapping("/hello")
    public String get() {
        log.debug("hello endpoint hit");
        return "<h2>Hello Ukraine</<h2>";
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<UserModel> getUserByEmail(@RequestParam("email") String email) {
        log.debug("fetch user endpoint hit");
        throwIfAuthNotBelongsToUser(email);
        try {
            UserModel userModel = userService.getUserByEmail(email);
            return ResponseEntity.ok().body(userModel);
        } catch (DataNotExistsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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

    private void throwIfAuthNotBelongsToUser(String email) {
        if (authChecker.checkIfAuthBelongsToUser(email)) {
            return;
        }
        log.error(AuthMessages.USER_NO_ACCESS_MSG);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, AuthMessages.USER_NO_ACCESS_MSG);
    }

}