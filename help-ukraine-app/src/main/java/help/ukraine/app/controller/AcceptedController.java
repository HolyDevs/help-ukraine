package help.ukraine.app.controller;

import help.ukraine.app.exception.*;
import help.ukraine.app.model.AcceptedModel;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.impl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(AcceptedController.ACCEPTED_ENDPOINT)
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AcceptedController {
    private final AcceptedService acceptedService;
    private final PendingService pendingService;
    private final PremiseOfferService premiseOfferService;
    private final SearchingOfferService searchingOfferService;
    private final AuthService authService;

    // MSGS

    private static final String NO_REQUIRED_PARAMS = "No required request params";

    // ENDPOINTS

    public static final String ACCEPTED_ENDPOINT = AuthUrls.BACKEND_ROOT + "/accepted";

    // CRUD
    @GetMapping(path = "/{searchingOfferId}/{premiseOfferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<AcceptedModel> getAccepted(@PathVariable Long searchingOfferId,
                                                    @PathVariable Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException, UserNoAccessException {
        throwIfAuthNotBelongToAllowedUsers(searchingOfferId, premiseOfferId);
        AcceptedModel acceptedModel = acceptedService.getAccepted(searchingOfferId, premiseOfferId);
        return ResponseEntity.ok().body(acceptedModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public List<AcceptedModel> getAccepteds(@RequestParam(required = false) Long premiseOfferId, @RequestParam(required = false) Long searchingOfferId) throws UserNoAccessException, PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        if (Objects.nonNull(premiseOfferId)) {
            throwIfAuthNotBelongToAllowedHost(premiseOfferId);
            return acceptedService.getAcceptedsByPremiseOfferId(premiseOfferId);
        }
        if (Objects.nonNull(searchingOfferId)) {
            throwIfAuthNotBelongToAllowedRefugee(searchingOfferId);
            return acceptedService.getAcceptedsBySearchingOfferId(searchingOfferId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_REQUIRED_PARAMS);
    }

    @DeleteMapping("/{searchingOfferId}/{pendingOfferId}")
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<AcceptedModel> deleteAccepted(@PathVariable Long searchingOfferId,
                                                      @PathVariable Long pendingOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException, UserNoAccessException {
        throwIfAuthNotBelongToAllowedUsers(searchingOfferId, pendingOfferId);
        acceptedService.deleteAccepted(searchingOfferId, pendingOfferId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<AcceptedModel> createAccepted(@RequestBody @Valid AcceptedModel acceptedModel) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedAlreadyCreatedException, PendingNotExistsException, UserNoAccessException {
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(acceptedModel.getPremiseOfferId());
        authService.throwIfAuthNotBelongToUser(premiseOfferModel.getHostId());
        pendingService.deletePendingsByPremiseOfferId(acceptedModel.getPremiseOfferId());
        pendingService.deletePendingsBySearchingOfferId(acceptedModel.getSearchingOfferId());
        AcceptedModel savedModel = acceptedService.createAccepted(acceptedModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    // EXCEPTION HANDLING

    @ExceptionHandler({
            AcceptedNotExistsException.class,
            PendingNotExistsException.class,
            PremiseOfferNotFoundException.class,
            SearchingOfferNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AcceptedAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflictExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenExceptions(Exception exception) {
        return exception.getMessage();
    }

    private void throwIfAuthNotBelongToAllowedUsers(Long searchingOfferId, Long premiseOfferId) throws UserNoAccessException, PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(searchingOfferId);
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(premiseOfferId);
        authService.throwIfAuthNotBelongToAnyUser(searchingOfferModel.getRefugeeId(), premiseOfferModel.getHostId());
    }

    private void throwIfAuthNotBelongToAllowedRefugee(Long searchingOfferId) throws UserNoAccessException, SearchingOfferNotFoundException {
        SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(searchingOfferId);
        authService.throwIfAuthNotBelongToUser(searchingOfferModel.getRefugeeId());
    }

    private void throwIfAuthNotBelongToAllowedHost(Long premiseOfferId) throws UserNoAccessException, PremiseOfferNotFoundException {
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(premiseOfferId);
        authService.throwIfAuthNotBelongToUser(premiseOfferModel.getHostId());
    }
}
