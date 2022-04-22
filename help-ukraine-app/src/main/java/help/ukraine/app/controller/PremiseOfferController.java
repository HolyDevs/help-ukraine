package help.ukraine.app.controller;

import help.ukraine.app.exception.FailedToSavePremiseOfferException;
import help.ukraine.app.exception.HostDoesNotExistException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.service.impl.PremiseOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("premises")
@RequiredArgsConstructor
@Log4j2
public class PremiseOfferController {
    private static final String ID_MISMATCH_MSG = "ID path variable and request body mismatch";

    private final PremiseOfferService premiseOfferService;

    @PostMapping
    public ResponseEntity<PremiseOfferModel> createPremiseOffer(@Valid @RequestBody PremiseOfferModel premiseOfferModel) {
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
    public List<PremiseOfferModel> getAllPremiseOffers(@RequestParam(required = false) Long hostId, @RequestParam(required = false) String hostEmail) {
        if (hostId != null) {
            return premiseOfferService.getAllPremiseOffersByHostId(hostId);
        }
        if (hostEmail != null) {
            return premiseOfferService.getAllPremiseOffersByHostEmail(hostEmail);
        }
        return premiseOfferService.getAllPremiseOffers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PremiseOfferModel> getPremiseOfferById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(premiseOfferService.getPremiseOfferById(id), HttpStatus.OK);
        } catch (PremiseOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePremiseOfferById(@PathVariable Long id) {
        try {
            premiseOfferService.deletePremiseOfferById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PremiseOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PremiseOfferModel> updatePremiseOfferById(@PathVariable Long id, @RequestBody PremiseOfferModel premiseOfferModel) {
        if (!premiseOfferModel.getId().equals(id)) {
            log.error(ID_MISMATCH_MSG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(premiseOfferService.updatePremiseOffer(premiseOfferModel), HttpStatus.OK);
        } catch (PremiseOfferNotFoundException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
