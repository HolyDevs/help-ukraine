package help.ukraine.app.service.impl;

import help.ukraine.app.data.PendingEntity;
import help.ukraine.app.data.PremiseOfferEntity;
import help.ukraine.app.data.SearchingOfferEntity;
import help.ukraine.app.data.SearchingPremiseOfferId;
import help.ukraine.app.exception.PendingAlreadyCreatedException;
import help.ukraine.app.exception.PendingNotExistsException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.exception.SearchingOfferNotFoundException;
import help.ukraine.app.model.PendingModel;
import help.ukraine.app.repository.PendingRepository;
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
public class PendingService {

    private final PendingRepository pendingRepository;
    private final SearchingOfferRepository searchingOfferRepository;
    private final PremiseOfferRepository premiseOfferRepository;
    private final MapperFacade pendingFacade;

    private static final String SEARCHING_OFFER_NOT_FOUND_MSG = "Searching offer with id %s not found";
    private static final String PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG = "Premise offer with id %s not found";
    private static final String PENDING_NOT_FOUND_EXCEPTION_MSG = "Pending with searching offer id %s and premise offer id %s not found";
    private static final String PENDING_ALREADY_CREATED_EXCEPTION_MSG = "Pending with searching offer id %s and premise offer id %s is already created";

    public PendingModel createPending(PendingModel pendingModel) throws SearchingOfferNotFoundException, PremiseOfferNotFoundException, PendingAlreadyCreatedException {
        SearchingPremiseOfferId composedId = getComposedId(pendingModel.getSearchingOfferId(), pendingModel.getPremiseOfferId());
        throwIfPendingAlreadyExists(composedId);
        PendingEntity pendingEntity = new PendingEntity(composedId);
        pendingEntity = pendingRepository.save(pendingEntity);
        return pendingFacade.map(pendingEntity, PendingModel.class);
    }

    public void deletePending(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, PendingNotExistsException {
        SearchingPremiseOfferId composedId = getComposedId(searchingOfferId, premiseOfferId);
        throwIfPendingNotExists(composedId);
        pendingRepository.deleteBySearchingPremiseOfferId(composedId);
    }

    public void deletePendingsByPremiseOfferIds(Long premiseOfferId) {
        pendingRepository.deleteByPremiseOfferId(premiseOfferId);
    }

    public PendingModel getPending(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException, PendingNotExistsException {
        SearchingPremiseOfferId composedId = getComposedId(searchingOfferId, premiseOfferId);
        PendingEntity pendingEntity = pendingRepository.findById(composedId)
                .orElseThrow(() -> new PendingNotExistsException(String.format(PENDING_NOT_FOUND_EXCEPTION_MSG, searchingOfferId, premiseOfferId)));
        return pendingFacade.map(pendingEntity, PendingModel.class);
    }

    public List<PendingModel> getPendingsBySearchingOfferId(Long searchingOfferId) {
        List<PendingEntity> pendingEntities = pendingRepository.getAllBySearchingOfferId(searchingOfferId);
        return pendingFacade.mapAsList(pendingEntities, PendingModel.class);
    }

    public List<PendingModel> getPendingsByPremiseOfferId(Long premiseOfferId) {
        List<PendingEntity> pendingEntities = pendingRepository.getAllByPremiseOfferId(premiseOfferId);
        return pendingFacade.mapAsList(pendingEntities, PendingModel.class);
    }

    public List<PendingModel> getAllPendings() {
        List<PendingEntity> pendingEntities = pendingRepository.findAll();
        return pendingFacade.mapAsList(pendingEntities, PendingModel.class);
    }

    private SearchingPremiseOfferId getComposedId(Long searchingOfferId, Long premiseOfferId) throws PremiseOfferNotFoundException, SearchingOfferNotFoundException {
        SearchingOfferEntity searchingOffer = searchingOfferRepository.findById(searchingOfferId)
                .orElseThrow(() -> new SearchingOfferNotFoundException(String.format(SEARCHING_OFFER_NOT_FOUND_MSG, searchingOfferId)));
        PremiseOfferEntity premiseOffer = premiseOfferRepository.findById(premiseOfferId)
                .orElseThrow(() -> new PremiseOfferNotFoundException(String.format(PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG, premiseOfferId)));
        return new SearchingPremiseOfferId(searchingOffer, premiseOffer);
    }

    private void throwIfPendingNotExists(SearchingPremiseOfferId composedId) throws PendingNotExistsException {
        if (pendingRepository.existsById(composedId)) {
            return;
        }
        String msg = String.format(PENDING_NOT_FOUND_EXCEPTION_MSG, composedId.getSearchingOffer().getId(), composedId.getPremiseOffer().getId());
        log.error(msg);
        throw new PendingNotExistsException(msg);
    }

    private void throwIfPendingAlreadyExists(SearchingPremiseOfferId composedId) throws PendingAlreadyCreatedException {
        if (!pendingRepository.existsById(composedId)) {
            return;
        }
        String msg = String.format(PENDING_ALREADY_CREATED_EXCEPTION_MSG, composedId.getSearchingOffer().getId(), composedId.getPremiseOffer().getId());
        log.error(msg);
        throw new PendingAlreadyCreatedException(msg);
    }
}
