package help.ukraine.app.service.impl;

import help.ukraine.app.data.SearchingOfferEntity;
import help.ukraine.app.data.SearchingPersonEntity;
import help.ukraine.app.exception.FailedToSaveSearchingOfferException;
import help.ukraine.app.exception.RefugeeDoesNotExistException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.repository.RefugeeRepository;
import help.ukraine.app.repository.SearchingOfferRepository;
import help.ukraine.app.repository.SearchingPersonRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchingOfferService {
    private final SearchingOfferRepository searchingOfferRepository;
    private final RefugeeRepository refugeeRepository;
    private final MapperFacade searchingOfferFacade;

    private static final String SEARCHING_OFFER_NOT_FOUND_MSG = "Searching offer with id %s not found";
    private static final String REFUGEE_NOT_FOUND_MSG = "Refugee with id %s not found";
    private final static String FAILED_TO_SAVE_EXCEPTION_MSG = "Failed to save searching offer";

    public SearchingOfferModel getSearchingOfferById(Long id) throws SearchingOfferNotFoundException {
        SearchingOfferEntity searchingOffer = searchingOfferRepository.findById(id)
                .orElseThrow(() -> new SearchingOfferNotFoundException(String.format(SEARCHING_OFFER_NOT_FOUND_MSG, id)));
        return searchingOfferFacade.map(searchingOffer, SearchingOfferModel.class);
    }

    public List<SearchingOfferModel> getAllSearchingOffers() {
        List<SearchingOfferEntity> allSearchingOffers = searchingOfferRepository.findAll();
        return searchingOfferFacade.mapAsList(allSearchingOffers, SearchingOfferModel.class);
    }

    public List<SearchingOfferModel> getSearchingOffersForRefugeeId(Long refugeeId) {
        List<SearchingOfferEntity> searchingOffersForRefugee = searchingOfferRepository.findByRefugeeId(refugeeId);
        return searchingOfferFacade.mapAsList(searchingOffersForRefugee, SearchingOfferModel.class);
    }

    public void deleteSearchingOfferById(Long id) throws SearchingOfferNotFoundException {
        SearchingOfferEntity searchingOffer = searchingOfferRepository.findById(id)
                .orElseThrow(() -> new SearchingOfferNotFoundException(String.format(SEARCHING_OFFER_NOT_FOUND_MSG, id)));
        searchingOfferRepository.delete(searchingOffer);
    }

    public SearchingOfferModel createSearchingOffer(SearchingOfferModel searchingOfferModel) throws RefugeeDoesNotExistException, FailedToSaveSearchingOfferException {
        refugeeRepository.findById(searchingOfferModel.getRefugeeId())
                .orElseThrow(() -> new RefugeeDoesNotExistException(String.format(REFUGEE_NOT_FOUND_MSG, searchingOfferModel.getRefugeeId())));
        SearchingOfferEntity searchingOffer = searchingOfferFacade.map(searchingOfferModel, SearchingOfferEntity.class);
        setSearchingOfferReferenceToSearchingPeople(searchingOffer);
        searchingOffer = searchingOfferRepository.save(searchingOffer);
        SearchingOfferEntity savedEntity = searchingOfferRepository.findById(searchingOffer.getId())
                .orElseThrow(() -> new FailedToSaveSearchingOfferException(FAILED_TO_SAVE_EXCEPTION_MSG));
        return searchingOfferFacade.map(savedEntity, SearchingOfferModel.class);
    }

    public SearchingOfferModel updateSearchingOffer(SearchingOfferModel searchingOfferModel) throws SearchingOfferNotFoundException, RefugeeDoesNotExistException, FailedToSaveSearchingOfferException {
        searchingOfferRepository.findById(searchingOfferModel.getId())
                .orElseThrow(() -> new SearchingOfferNotFoundException(String.format(SEARCHING_OFFER_NOT_FOUND_MSG, searchingOfferModel.getId())));
        return createSearchingOffer(searchingOfferModel);
    }

    private void setSearchingOfferReferenceToSearchingPeople(SearchingOfferEntity searchingOffer) {
        for (SearchingPersonEntity searchingPersonEntity : searchingOffer.getSearchingPeople()) {
            searchingPersonEntity.setSearchingOffer(searchingOffer);
        }
    }

}
