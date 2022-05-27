package help.ukraine.app.controller;

import help.ukraine.app.dto.CandidateDto;
import help.ukraine.app.dto.UserContactDataDto;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.exception.UserNotExistsException;
import help.ukraine.app.model.PendingModel;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthRoles;
import help.ukraine.app.security.constants.AuthUrls;
import help.ukraine.app.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(CandidateController.CANDIDATE_DATA_ENDPOINT)
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CandidateController {

    private final UserService userService;
    private final SearchingOfferService searchingOfferService;
    private final PendingService pendingService;

    // ENDPOINTS
    public static final String CANDIDATE_DATA_ENDPOINT = AuthUrls.BACKEND_ROOT + "/candidate";

    // CRUD
    @GetMapping(path = "/{premiseOfferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthRoles.HOST_ROLE)
    public List<CandidateDto> getCandidates(@PathVariable Long premiseOfferId) throws UserNotExistsException, SearchingOfferNotFoundException {

        List<SearchingOfferModel> searchingOfferModels = new ArrayList<>();
        for (PendingModel pendingModel : pendingService.getPendingsByPremiseOfferId(premiseOfferId)) {
            SearchingOfferModel searchingOfferModel = searchingOfferService.getSearchingOfferById(pendingModel.getSearchingOfferId());
            searchingOfferModels.add(searchingOfferModel);
        }
        List<CandidateDto> candidateDtos = new ArrayList<>();
        for (SearchingOfferModel searchingOfferModel : searchingOfferModels) {
            UserModel refugee = userService.getUserById(searchingOfferModel.getRefugeeId());
            CandidateDto candidateDto = CandidateDto.builder()
                    .name(refugee.getName())
                    .surname(refugee.getSurname())
                    .email(refugee.getEmail())
                    .phoneNumber(refugee.getEmail())
                    .sex(refugee.getSex())
                    .movingIssues(searchingOfferModel.getUserMovingIssues())
                    .birthDate(refugee.getBirthDate())
                    .searchingPeople(searchingOfferModel.getSearchingPeople())
                    .animalsInvolved(searchingOfferModel.getAnimalsInvolved())
                    .additionalInfo(searchingOfferModel.getAdditionalInfo())
                    .build();
            candidateDtos.add(candidateDto);
        }
        return candidateDtos;
    }

    // EXCEPTION HANDLING

    @ExceptionHandler({
            UserNotExistsException.class,
            SearchingOfferNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExceptions(Exception exception) {
        return exception.getMessage();
    }
}

