package help.ukraine.app.service.impl;

import help.ukraine.app.data.AcceptedEntity;
import help.ukraine.app.data.PremiseOfferEntity;
import help.ukraine.app.data.SearchingOfferEntity;
import help.ukraine.app.data.SearchingPremiseOfferId;
import help.ukraine.app.exception.AcceptedAlreadyCreatedException;
import help.ukraine.app.exception.AcceptedNotExistsException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.AcceptedModel;
import help.ukraine.app.repository.AcceptedRepository;
import help.ukraine.app.repository.PremiseOfferRepository;
import help.ukraine.app.repository.SearchingOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AcceptedService {
    private final AcceptedRepository acceptedRepository;
    private final SearchingOfferRepository searchingOfferRepository;
    private final PremiseOfferRepository premiseOfferRepository;
    private final MapperFacade acceptedFacade;

    private static final String SEARCHING_OFFER_NOT_FOUND_MSG = "Searching offer with id %s not found";
    private static final String PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG = "Premise offer with id %s not found";
    private static final String ACCEPTED_NOT_FOUND_EXCEPTION_MSG = "Accepted with searching offer id %s and premise offer id %s not found";
    private static final String ACCEPTED_ALREADY_CREATED_EXCEPTION_MSG = "Accepted with searching offer id %s and premise offer id %s is already created";

    public AcceptedModel createAccepted(AcceptedModel acceptedModel) throws SearchingOfferNotFoundException, PremiseOfferNotFoundException, AcceptedAlreadyCreatedException {
        SearchingOfferEntity searchingOffer = getSearchingOfferEntity(acceptedModel.getSearchingOfferId());
        PremiseOfferEntity premiseOffer = getPremiseOfferEntity(acceptedModel.getPremiseOfferId());
        SearchingPremiseOfferId composedId = new SearchingPremiseOfferId(searchingOffer, premiseOffer);
        throwIfAcceptedAlreadyExists(composedId);
        deactivatePremiseOfferEntity(premiseOffer);
        AcceptedEntity acceptedEntity = new AcceptedEntity(composedId);
        acceptedEntity = acceptedRepository.save(acceptedEntity);
        return acceptedFacade.map(acceptedEntity, AcceptedModel.class);
    }

    public void deleteAccepted(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException {
        SearchingPremiseOfferId composedId = getComposedId(searchingOfferId, premiseOfferId);
        throwIfAcceptedNotExists(composedId);
        acceptedRepository.deleteBySearchingPremiseOfferId(composedId);
    }

    public AcceptedModel getAccepted(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, AcceptedNotExistsException {
        SearchingPremiseOfferId composedId = getComposedId(searchingOfferId, premiseOfferId);
        AcceptedEntity acceptedEntity = acceptedRepository.findById(composedId)
                .orElseThrow(() -> new AcceptedNotExistsException(String.format(ACCEPTED_NOT_FOUND_EXCEPTION_MSG, searchingOfferId, premiseOfferId)));
        return acceptedFacade.map(acceptedEntity, AcceptedModel.class);
    }

    public List<AcceptedModel> getAcceptedsBySearchingOfferId(Long searchingOfferId) {
        List<AcceptedEntity> acceptedEntities = acceptedRepository.getAllBySearchingOfferId(searchingOfferId);
        return acceptedFacade.mapAsList(acceptedEntities, AcceptedModel.class);
    }

    public List<AcceptedModel> getAcceptedsByPremiseOfferId(Long premiseOfferId) {
        List<AcceptedEntity> acceptedEntities = acceptedRepository.getAllByPremiseOfferId(premiseOfferId);
        return acceptedFacade.mapAsList(acceptedEntities, AcceptedModel.class);
    }

    public List<AcceptedModel> getAllAccepteds() {
        List<AcceptedEntity> acceptedEntities = acceptedRepository.findAll();
        return acceptedFacade.mapAsList(acceptedEntities, AcceptedModel.class);
    }

    private PremiseOfferEntity getPremiseOfferEntity(Long premiseOfferId) throws PremiseOfferNotFoundException {
        return premiseOfferRepository.findById(premiseOfferId)
                .orElseThrow(() -> new PremiseOfferNotFoundException(String.format(PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG, premiseOfferId)));
    }

    private SearchingOfferEntity getSearchingOfferEntity(Long searchingOfferId) throws SearchingOfferNotFoundException {
        return searchingOfferRepository.findById(searchingOfferId)
                .orElseThrow(() -> new SearchingOfferNotFoundException(String.format(SEARCHING_OFFER_NOT_FOUND_MSG, searchingOfferId)));
    }

    private void deactivatePremiseOfferEntity(PremiseOfferEntity premiseOfferEntity) {
        premiseOfferEntity.setActive(false);
        premiseOfferRepository.save(premiseOfferEntity);
    }

    private SearchingPremiseOfferId getComposedId(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        SearchingOfferEntity searchingOffer = getSearchingOfferEntity(searchingOfferId);
        PremiseOfferEntity premiseOffer = getPremiseOfferEntity(premiseOfferId);
        return new SearchingPremiseOfferId(searchingOffer, premiseOffer);
    }

    private void throwIfAcceptedNotExists(SearchingPremiseOfferId composedId) throws AcceptedNotExistsException {
        if (acceptedRepository.existsById(composedId)) {
            return;
        }
        String msg = String.format(ACCEPTED_NOT_FOUND_EXCEPTION_MSG, composedId.getSearchingOffer().getId(), composedId.getPremiseOffer().getId());
        log.error(msg);
        throw new AcceptedNotExistsException(msg);
    }

    private void throwIfAcceptedAlreadyExists(SearchingPremiseOfferId composedId) throws AcceptedAlreadyCreatedException {
        if (!acceptedRepository.existsById(composedId)) {
            return;
        }
        String msg = String.format(ACCEPTED_ALREADY_CREATED_EXCEPTION_MSG, composedId.getSearchingOffer().getId(), composedId.getPremiseOffer().getId());
        log.error(msg);
        throw new AcceptedAlreadyCreatedException(msg);
    }
}
