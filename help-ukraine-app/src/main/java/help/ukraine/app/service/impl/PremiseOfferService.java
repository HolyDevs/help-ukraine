package help.ukraine.app.service.impl;

import help.ukraine.app.data.OfferImageEntity;
import help.ukraine.app.data.PremiseOfferEntity;
import help.ukraine.app.exception.FailedToSavePremiseOfferException;
import help.ukraine.app.exception.HostDoesNotExistException;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.repository.HostRepository;
import help.ukraine.app.repository.OfferImagesRepository;
import help.ukraine.app.repository.PremiseOfferRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PremiseOfferService {
    private final static String PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG = "Premise offer with id %s not found";
    private final static String HOST_NOT_FOUND_EXCEPTION_MSG = "Host with id %s not found";
    private final static String FAILED_TO_SAVE_EXCEPTION_MSG = "Failed to save premise offer";

    private final PremiseOfferRepository premiseOfferRepository;
    private final HostRepository hostRepository;
    private final MapperFacade premiseOfferFacade;

    public PremiseOfferModel getPremiseOfferById(Long id) throws PremiseOfferNotFoundException {
        PremiseOfferEntity premiseOffer = premiseOfferRepository.findById(id)
                .orElseThrow(() -> new PremiseOfferNotFoundException(String.format(PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG, id)));
        return premiseOfferFacade.map(premiseOffer, PremiseOfferModel.class);
    }

    public List<PremiseOfferModel> getAllPremiseOffers() {
        List<PremiseOfferEntity> premiseOffers = premiseOfferRepository.findAll();
        return premiseOfferFacade.mapAsList(premiseOffers, PremiseOfferModel.class);
    }

    public List<PremiseOfferModel> getAllPremiseOffersByHostId(Long hostId) {
        List<PremiseOfferEntity> premiseOffersByHostId = premiseOfferRepository.findByHostId(hostId);
        return premiseOfferFacade.mapAsList(premiseOffersByHostId, PremiseOfferModel.class);
    }

    public List<PremiseOfferModel> getAllPremiseOffersByHostEmail(String email) {
        List<PremiseOfferEntity> premiseOffersByHostEmail = premiseOfferRepository.findByHostUserEmail(email);
        return premiseOfferFacade.mapAsList(premiseOffersByHostEmail, PremiseOfferModel.class);
    }

    public void deletePremiseOfferById(Long id) throws PremiseOfferNotFoundException {
        PremiseOfferEntity premiseOffer = premiseOfferRepository.findById(id)
                .orElseThrow(() -> new PremiseOfferNotFoundException(String.format(PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG, id)));
        premiseOfferRepository.delete(premiseOffer);
    }

    public PremiseOfferModel createPremiseOffer(PremiseOfferModel premiseOfferModel) throws HostDoesNotExistException, FailedToSavePremiseOfferException {
        hostRepository.findById(premiseOfferModel.getHostId())
                .orElseThrow(() -> new HostDoesNotExistException(String.format(HOST_NOT_FOUND_EXCEPTION_MSG, premiseOfferModel.getHostId())));
        PremiseOfferEntity premiseOfferEntity = premiseOfferFacade.map(premiseOfferModel, PremiseOfferEntity.class);
        setPremiseOfferReferenceToOfferImages(premiseOfferEntity);
        premiseOfferEntity = premiseOfferRepository.save(premiseOfferEntity);
        PremiseOfferEntity savedEntity = premiseOfferRepository.findById(premiseOfferEntity.getId())
                .orElseThrow(() -> new FailedToSavePremiseOfferException(FAILED_TO_SAVE_EXCEPTION_MSG));
        return premiseOfferFacade.map(savedEntity, PremiseOfferModel.class);
    }

    public PremiseOfferModel updatePremiseOffer(PremiseOfferModel premiseOfferModel) throws PremiseOfferNotFoundException, FailedToSavePremiseOfferException, HostDoesNotExistException {
        premiseOfferRepository.findById(premiseOfferModel.getId())
                .orElseThrow(() -> new PremiseOfferNotFoundException(String.format(PREMISE_OFFER_NOT_FOUND_EXCEPTION_MSG, premiseOfferModel.getId())));
        return createPremiseOffer(premiseOfferModel);
    }

    private void setPremiseOfferReferenceToOfferImages(PremiseOfferEntity premiseOffer) {
        if (Objects.isNull(premiseOffer.getOfferImages())) {
            return;
        }
        for (OfferImageEntity offerImage : premiseOffer.getOfferImages()) {
            offerImage.setPremiseOffer(premiseOffer);
        }
    }
}
