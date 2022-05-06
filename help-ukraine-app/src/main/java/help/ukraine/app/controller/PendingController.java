package help.ukraine.app.controller;

import help.ukraine.app.exception.PendingAlreadyCreatedException;
import help.ukraine.app.exception.PendingNotExistsException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.PendingModel;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.impl.PendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(PendingController.PENDING_ENDPOINT)
@RequiredArgsConstructor
@Log4j2
public class PendingController {

    private final PendingService pendingService;

    // ENDPOINTS
    public static final String PENDING_ENDPOINT = AuthUrls.BACKEND_ROOT + "/pending";

    // CRUD
    @GetMapping(path = "/{searchingOfferId}/{premiseOfferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PendingModel> getPending(@PathVariable Long searchingOfferId,
                                                   @PathVariable Long premiseOfferId) throws PendingNotExistsException, PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        PendingModel pendingModel = pendingService.getPending(searchingOfferId, premiseOfferId);
        return ResponseEntity.ok().body(pendingModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PendingModel> getPendings(@RequestParam(required = false) Long searchingOfferId, @RequestParam(required = false) Long premiseOfferId) {
        if (Objects.nonNull(searchingOfferId)) {
            return pendingService.getPendingsBySearchingOfferId(searchingOfferId);
        }
        if (Objects.nonNull(premiseOfferId)) {
            return pendingService.getPendingsByPremiseOfferId(premiseOfferId);
        }
        return pendingService.getAllPendings();
    }

    @DeleteMapping("/{searchingOfferId}/{pendingOfferId}")
    public ResponseEntity<PendingModel> deletePending(@PathVariable Long searchingOfferId,
                                                      @PathVariable Long pendingOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        pendingService.deletePending(searchingOfferId, pendingOfferId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PendingModel> createPending(@RequestBody @Valid PendingModel pendingModel) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, PendingAlreadyCreatedException {
        PendingModel savedModel = pendingService.createPending(pendingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    // EXCEPTION HANDLING

    @ExceptionHandler(PremiseOfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePremiseOfferNotFoundException(PremiseOfferNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(SearchingOfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSearchingOfferNotFoundException(SearchingOfferNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PendingNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePendingNotExistsException(PendingNotExistsException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PendingAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePendingAlreadyCreatedException(PendingAlreadyCreatedException exception) {
        return exception.getMessage();
    }
}

