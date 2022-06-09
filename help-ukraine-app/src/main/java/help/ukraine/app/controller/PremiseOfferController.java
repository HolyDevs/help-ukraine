package help.ukraine.app.controller;

import help.ukraine.app.exception.FailedToSavePremiseOfferException;
import help.ukraine.app.exception.HostDoesNotExistException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.impl.PremiseOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(AuthUrls.BACKEND_ROOT + "/premise-offers")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PremiseOfferController {
    private static final String ID_MISMATCH_MSG = "ID path variable and request body mismatch";

    private final PremiseOfferService premiseOfferService;
    private final AuthService authService;

    @PostMapping
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<PremiseOfferModel> createPremiseOffer(@Valid @RequestBody PremiseOfferModel premiseOfferModel) throws UserNoAccessException {
        authService.throwIfAuthNotBelongToUser(premiseOfferModel.getHostId());
        try {
            return new ResponseEntity<>(premiseOfferService.createPremiseOffer(premiseOfferModel), HttpStatus.OK);
        } catch (HostDoesNotExistException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FailedToSavePremiseOfferException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Secured(AuthRoles.HOST_ROLE)
    public List<PremiseOfferModel> getPremiseOffersByHostId(@RequestParam Long hostId) throws UserNoAccessException {
        authService.throwIfAuthNotBelongToUser(hostId);
        return premiseOfferService.getAllPremiseOffersByHostId(hostId);
    }

    @GetMapping("/search")
    @Secured(AuthRoles.REFUGEE_ROLE)
    public List<PremiseOfferModel> filterPremiseOffers(@RequestParam(required = false) Boolean movingIssues,
                                                       @RequestParam(required = false) Boolean animalsInvolved,
                                                       @RequestParam(required = false) Integer numberOfPeople,
                                                       @RequestParam(required = false) Integer count)  {
        if (Objects.isNull(count)) {
            count = 10;
        }
        return premiseOfferService.filterPremiseOffers(animalsInvolved, movingIssues, numberOfPeople, count);
    }

    @GetMapping(path = "/{id}")
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<PremiseOfferModel> getPremiseOfferById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(premiseOfferService.getPremiseOfferById(id), HttpStatus.OK);
        } catch (PremiseOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "/{id}")
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<Void> deletePremiseOfferById(@PathVariable Long id) throws UserNoAccessException {
        try {
            PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(id);
            authService.throwIfAuthNotBelongToUser(premiseOfferModel.getHostId());
            premiseOfferService.deletePremiseOfferById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PremiseOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<PremiseOfferModel> updatePremiseOfferById(@PathVariable Long id, @Valid @RequestBody PremiseOfferModel premiseOfferModel) throws UserNoAccessException {
        if (!premiseOfferModel.getId().equals(id)) {
            log.error(ID_MISMATCH_MSG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        authService.throwIfAuthNotBelongToUser(premiseOfferModel.getHostId());

        try {
            return new ResponseEntity<>(premiseOfferService.updatePremiseOffer(premiseOfferModel), HttpStatus.OK);
        } catch (PremiseOfferNotFoundException | HostDoesNotExistException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FailedToSavePremiseOfferException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenExceptions(Exception exception) {
        return exception.getMessage();
    }
}
