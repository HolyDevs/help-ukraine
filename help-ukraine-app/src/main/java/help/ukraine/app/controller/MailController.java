package help.ukraine.app.controller;

import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.UserService;
import help.ukraine.app.service.impl.MailService;
import help.ukraine.app.service.impl.PremiseOfferService;
import help.ukraine.app.service.impl.SearchingOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@RestController
@RequestMapping(AuthUrls.BACKEND_ROOT + "/mail")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MailController {
    private final UserService userService;
    private final PremiseOfferService premiseOfferService;
    private final SearchingOfferService searchingOfferService;
    private final MailService mailService;
    private final AuthService authService;

    @PostMapping(path = "/pending/{premiseOfferId}/{refugeeId}")
    @Secured(AuthRoles.REFUGEE_ROLE)
    public ResponseEntity<String> sendPendingNotificationMail(@PathVariable Long premiseOfferId, @PathVariable Long refugeeId) throws PremiseOfferNotFoundException, UserNotExistsException, MessagingException, UserNoAccessException {
        authService.throwIfAuthNotBelongToUser(refugeeId);
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(premiseOfferId);
        UserModel refugee = userService.getUserById(refugeeId);
        UserModel host = userService.getUserById(premiseOfferModel.getHostId());
        mailService.sendPendingNotificationMail(host, refugee, premiseOfferModel);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping(path = "/accepted/{searchingOfferId}/{hostId}")
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<String> sendAcceptedNotificationMail(@PathVariable Long searchingOfferId, @PathVariable Long hostId) throws PremiseOfferNotFoundException, UserNotExistsException, MessagingException, UserNoAccessException, SearchingOfferNotFoundException {
        SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(searchingOfferId);
        authService.throwIfAuthNotBelongToUser(hostId);
        UserModel refugee = userService.getUserById(searchingOfferModel.getRefugeeId());
        UserModel host = userService.getUserById(hostId);
        mailService.sendAcceptedNotificationMail(host, refugee);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping(path = "/rejected/{searchingOfferId}/{hostId}")
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<String> sendRejectedNotificationMail(@PathVariable Long searchingOfferId, @PathVariable Long hostId) throws PremiseOfferNotFoundException, UserNotExistsException, MessagingException, UserNoAccessException, SearchingOfferNotFoundException {
        SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(searchingOfferId);
        authService.throwIfAuthNotBelongToUser(hostId);
        UserModel refugee = userService.getUserById(searchingOfferModel.getRefugeeId());
        UserModel host = userService.getUserById(hostId);
        mailService.sendRejectedNotificationMail(host, refugee);
        return ResponseEntity.ok("Message sent");
    }



    // EXCEPTION HANDLING

    @ExceptionHandler({
            UserNotExistsException.class,
            PremiseOfferNotFoundException.class,
            SearchingOfferNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenExceptions(Exception exception) {
        return exception.getMessage();
    }
}
