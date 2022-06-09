package help.ukraine.app.controller;

import help.ukraine.app.dto.ContactDataDto;
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
@RequestMapping(ContactDataController.CONTACT_DATA_ENDPOINT)
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ContactDataController {

    private final UserService userService;

    // ENDPOINTS

    public static final String CONTACT_DATA_ENDPOINT = AuthUrls.BACKEND_ROOT + "/contact-data";

    // CRUD

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<ContactDataDto> getContactDataDto(@PathVariable Long userId) throws UserNotExistsException {
        UserModel userModel = userService.getUserById(userId);
        ContactDataDto contactDataDto = buildContactDataDto(userModel);
        return ResponseEntity.ok(contactDataDto);
    }

    private ContactDataDto buildContactDataDto(UserModel userModel) {
        return ContactDataDto.builder()
                .email(userModel.getEmail())
                .phoneNumber(userModel.getPhoneNumber())
                .name(userModel.getName())
                .surname(userModel.getSurname())
                .build();
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }
}
