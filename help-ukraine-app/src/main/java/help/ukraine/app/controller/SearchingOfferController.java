package help.ukraine.app.controller;

import help.ukraine.app.exception.FailedToSaveSearchingOfferException;
import help.ukraine.app.exception.RefugeeDoesNotExistException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.security.exception.UserNoAccessException;
import help.ukraine.app.security.service.AuthService;
import help.ukraine.app.service.impl.SearchingOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AuthUrls.BACKEND_ROOT + "/searching-offers")
@RequiredArgsConstructor
@Transactional
@Log4j2
public class SearchingOfferController {
    private static final String ID_MISMATCH_MSG = "ID path variable and request body mismatch";

    private final SearchingOfferService searchingOfferService;
    private final AuthService authService;

    @PostMapping
    @Secured(AuthRoles.REFUGEE_ROLE)
    public ResponseEntity<SearchingOfferModel> createSearchingOffer(@Valid @RequestBody SearchingOfferModel searchingOfferModel) throws UserNoAccessException {
        authService.throwIfAuthNotBelongToUser(searchingOfferModel.getRefugeeId());
        try {
            return new ResponseEntity<>(searchingOfferService.createSearchingOffer(searchingOfferModel), HttpStatus.OK);
        } catch (RefugeeDoesNotExistException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FailedToSaveSearchingOfferException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public List<SearchingOfferModel> getAllSearchingOffers(@RequestParam(required = false) Long refugeeId) {
        if (refugeeId != null) {
            return searchingOfferService.getSearchingOffersForRefugeeId(refugeeId);
        }
        return searchingOfferService.getAllSearchingOffers();
    }

    @GetMapping(path = "/{id}")
    @Secured({AuthRoles.REFUGEE_ROLE, AuthRoles.HOST_ROLE})
    public ResponseEntity<SearchingOfferModel> getSearchingOfferById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(searchingOfferService.getSearchingOfferById(id), HttpStatus.OK);
        } catch (SearchingOfferNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @Secured(AuthRoles.HOST_ROLE)
    public ResponseEntity<Void> deleteSearchingOfferById(@PathVariable Long id) throws UserNoAccessException {
        try {
            SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(id);
            authService.throwIfAuthNotBelongToUser(searchingOfferModel.getRefugeeId());
            searchingOfferService.deleteSearchingOfferById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SearchingOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    @Secured(AuthRoles.REFUGEE_ROLE)
    public ResponseEntity<SearchingOfferModel> updateSearchingOfferById(@PathVariable Long id, @Valid @RequestBody SearchingOfferModel searchingOfferModel) throws UserNoAccessException {
        if (!searchingOfferModel.getId().equals(id)) {
            log.error(ID_MISMATCH_MSG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        authService.throwIfAuthNotBelongToUser(searchingOfferModel.getRefugeeId());

        try {
            return new ResponseEntity<>(searchingOfferService.updateSearchingOffer(searchingOfferModel), HttpStatus.OK);
        } catch (SearchingOfferNotFoundException | RefugeeDoesNotExistException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FailedToSaveSearchingOfferException e) {
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
