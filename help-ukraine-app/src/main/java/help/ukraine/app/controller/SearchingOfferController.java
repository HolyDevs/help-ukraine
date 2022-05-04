package help.ukraine.app.controller;

import help.ukraine.app.exception.FailedToSaveSearchingOfferException;
import help.ukraine.app.exception.RefugeeDoesNotExistException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.impl.SearchingOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AuthUrls.BACKEND_ROOT + "/refugee-offers")
@RequiredArgsConstructor
@Log4j2
public class SearchingOfferController {
    private static final String ID_MISMATCH_MSG = "ID path variable and request body mismatch";

    private final SearchingOfferService searchingOfferService;

    @PostMapping
    public ResponseEntity<SearchingOfferModel> createSearchingOffer(@Valid @RequestBody SearchingOfferModel searchingOfferModel) {
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
    public List<SearchingOfferModel> getAllSearchingOffers(@RequestParam(required = false) Long refugeeId) {
        if (refugeeId != null) {
            return searchingOfferService.getSearchingOffersForRefugeeId(refugeeId);
        }
        return searchingOfferService.getAllSearchingOffers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SearchingOfferModel> getSearchingOfferById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(searchingOfferService.getSearchingOfferById(id), HttpStatus.OK);
        } catch (SearchingOfferNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteSearchingOfferById(@PathVariable Long id) {
        try {
            searchingOfferService.deleteSearchingOfferById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SearchingOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SearchingOfferModel> updateSearchingOfferById(@PathVariable Long id, @Valid @RequestBody SearchingOfferModel searchingOfferModel) {
        if (!searchingOfferModel.getId().equals(id)) {
            log.error(ID_MISMATCH_MSG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
}
