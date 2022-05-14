package help.ukraine.app.controller;

import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.UserNoAccessException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.MailService;
import help.ukraine.app.service.UserService;
import help.ukraine.app.service.impl.PremiseOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping(AuthUrls.BACKEND_ROOT + "/mail")
@RequiredArgsConstructor
@Log4j2
public class MailController {

    private final UserService userService;
    private final PremiseOfferService premiseOfferService;
    private final MailService mailService;

    @PostMapping(path = "/{premiseOfferId}/{refugeeId}")
    public ResponseEntity<String> sendOfferNotificationMail(@PathVariable Long premiseOfferId, @PathVariable Long refugeeId) throws PremiseOfferNotFoundException, UserNotExistsException, MessagingException {
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(premiseOfferId);
        UserModel refugee = userService.fetchUserById(refugeeId);
        UserModel host = userService.fetchUserById(premiseOfferModel.getHostId());
        String offerAddress = parseAddress(premiseOfferModel);
        mailService.sendOfferNotificationMail(host.getEmail(), host.getName(), refugee.getName(), offerAddress);
        return ResponseEntity.ok("Message sent");
    }

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotExistsException(UserNotExistsException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PremiseOfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePremiseOfferNotFoundException(PremiseOfferNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessagingExceptionException(MessagingException exception) {
        return exception.getMessage();
    }

    private String parseAddress(PremiseOfferModel premiseOfferModel) {
        return premiseOfferModel.getCity() + ", " + premiseOfferModel.getStreet() + " "
                + premiseOfferModel.getHouseNumber() + ", "
                + premiseOfferModel.getPostalCode();
    }
}
