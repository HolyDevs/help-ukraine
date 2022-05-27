package help.ukraine.app.controller;

import help.ukraine.app.dto.UserContactDataDto;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.UserModel;
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

import javax.transaction.Transactional;

@RestController
@RequestMapping(UserContactDataController.CONTACT_DATA_ENDPOINT)
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserContactDataController {

    private final UserService userService;

    // ENDPOINTS
    public static final String CONTACT_DATA_ENDPOINT = AuthUrls.BACKEND_ROOT + "/contact";

    // CRUD
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<UserContactDataDto> getUserContactData(@PathVariable Long userId) throws UserNotExistsException {
        UserModel userModel = userService.getUserById(userId);
        UserContactDataDto contactData = UserContactDataDto.builder()
                .name(userModel.getName())
                .surname(userModel.getSurname())
                .birthDate(userModel.getBirthDate())
                .phoneNumber(userModel.getPhoneNumber())
                .email(userModel.getEmail())
                .sex(userModel.getSex())
                .build();
        return ResponseEntity.ok().body(contactData);
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }
}
