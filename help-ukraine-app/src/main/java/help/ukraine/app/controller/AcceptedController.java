package help.ukraine.app.controller;

import help.ukraine.app.exception.AcceptedAlreadyCreatedException;
import help.ukraine.app.exception.AcceptedNotExistsException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.AcceptedModel;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.impl.AcceptedService;
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
@RequestMapping(AcceptedController.ACCEPTED_ENDPOINT)
@RequiredArgsConstructor
@Log4j2
public class AcceptedController {
    private final AcceptedService acceptedService;

    // ENDPOINTS
    public static final String ACCEPTED_ENDPOINT = AuthUrls.BACKEND_ROOT + "/accepted";

    // CRUD
    @GetMapping(path = "/{searchingOfferId}/{premiseOfferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcceptedModel> getAccepted(@PathVariable Long searchingOfferId,
                                                    @PathVariable Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException {
        AcceptedModel acceptedModel = acceptedService.getAccepted(searchingOfferId, premiseOfferId);
        return ResponseEntity.ok().body(acceptedModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AcceptedModel> getAccepteds(@RequestParam(required = false) Long searchingOfferId, @RequestParam(required = false) Long premiseOfferId) {
        if (Objects.nonNull(searchingOfferId)) {
            return acceptedService.getAcceptedsBySearchingOfferId(searchingOfferId);
        }
        if (Objects.nonNull(premiseOfferId)) {
            return acceptedService.getAcceptedsByPremiseOfferId(premiseOfferId);
        }
        return acceptedService.getAllAccepteds();
    }

    @DeleteMapping("/{searchingOfferId}/{pendingOfferId}")
    public ResponseEntity<AcceptedModel> deleteAccepted(@PathVariable Long searchingOfferId,
                                                      @PathVariable Long pendingOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException {
        acceptedService.deleteAccepted(searchingOfferId, pendingOfferId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcceptedModel> createAccepted(@RequestBody @Valid AcceptedModel acceptedModel) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedAlreadyCreatedException {
        AcceptedModel savedModel = acceptedService.createAccepted(acceptedModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    // EXCEPTION HANDLING

    @ExceptionHandler({
            AcceptedNotExistsException.class,
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
}
