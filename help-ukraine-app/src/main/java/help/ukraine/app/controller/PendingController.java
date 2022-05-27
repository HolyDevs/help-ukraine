package help.ukraine.app.controller;

import help.ukraine.app.exception.PendingAlreadyCreatedException;
import help.ukraine.app.exception.PendingNotExistsException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.PendingModel;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.impl.PendingService;
import help.ukraine.app.service.impl.PremiseOfferService;
import help.ukraine.app.service.impl.SearchingOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(PendingController.PENDING_ENDPOINT)
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PendingController {

    private final PendingService pendingService;
    private final SearchingOfferService searchingOfferService;
    private final PremiseOfferService premiseOfferService;
    private final AuthService authService;

    // ENDPOINTS

    public static final String PENDING_ENDPOINT = AuthUrls.BACKEND_ROOT + "/pending";

    // CRUD

    @GetMapping(path = "/{searchingOfferId}/{premiseOfferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<PendingModel> getPending(@PathVariable Long searchingOfferId,
                                                   @PathVariable Long premiseOfferId) throws PendingNotExistsException, PremiseOfferNotFoundException, SearchingOfferNotFoundException, UserNoAccessException {
        throwIfAuthNotBelongToAllowedUsers(searchingOfferId, premiseOfferId);
        PendingModel pendingModel = pendingService.getPending(searchingOfferId, premiseOfferId);
        return ResponseEntity.ok().body(pendingModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public List<PendingModel> getPendings(@RequestParam(required = false) Long searchingOfferId, @RequestParam(required = false) Long premiseOfferId) throws UserNoAccessException, PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        throwIfAuthNotBelongToAllowedUsers(searchingOfferId, premiseOfferId);
        if (Objects.nonNull(searchingOfferId)) {
            return pendingService.getPendingsBySearchingOfferId(searchingOfferId);
        }
        if (Objects.nonNull(premiseOfferId)) {
            return pendingService.getPendingsByPremiseOfferId(premiseOfferId);
        }
        return pendingService.getAllPendings();
    }

    @DeleteMapping("/{searchingOfferId}/{premiseOfferId}")
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<PendingModel> deletePending(@PathVariable Long searchingOfferId,
                                                      @PathVariable Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, PendingNotExistsException, UserNoAccessException {
        throwIfAuthNotBelongToAllowedUsers(searchingOfferId, premiseOfferId);
        pendingService.deletePending(searchingOfferId, premiseOfferId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthRoles.REFUGEE_ROLE)
    public ResponseEntity<PendingModel> createPending(@RequestBody @Valid PendingModel pendingModel) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, PendingAlreadyCreatedException, UserNoAccessException {
        SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(pendingModel.getSearchingOfferId());
        authService.throwIfAuthNotBelongToUser(searchingOfferModel.getRefugeeId());
        PendingModel savedModel = pendingService.createPending(pendingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    // EXCEPTION HANDLING

    @ExceptionHandler({
            PendingNotExistsException.class,
            SearchingOfferNotFoundException.class,
            PremiseOfferNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PendingAlreadyCreatedException.class)
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
}

