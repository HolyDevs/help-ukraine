package help.ukraine.app.unit.service;

import help.ukraine.app.data.HostEntity;
import help.ukraine.app.data.OfferImageEntity;
import help.ukraine.app.data.PremiseOfferEntity;
import help.ukraine.app.data.UserEntity;
import help.ukraine.app.exception.PremiseOfferNotFoundException;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.repository.PremiseOfferRepository;
import help.ukraine.app.service.impl.PremiseOfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
public class PremiseOfferServiceTest {
    @MockBean
    private PremiseOfferRepository premiseOfferRepository;

    @Autowired
    private PremiseOfferService premiseOfferService;

    @MockBean
    private JavaMailSender javaMailSender;

    private static PremiseOfferEntity PREMISE_OFFER_1;
    private static final Long PREMISE_OFFER_1_ID = 1L;
    private static final int PREMISE_OFFER_1_PEOPLE_COUNT = 2;
    private static final int PREMISE_OFFER_1_KITCHENS_COUNT = 1;
    private static final int PREMISE_OFFER_1_BEDROOMS_COUNT = 1;
    private static final int PREMISE_OFFER_1_BATHROOMS_COUNT = 1;
    private static final boolean PREMISE_OFFER_1_ANIMALS_ALLOWED = true;
    private static final boolean PREMISE_OFFER_1_WHEELCHAIR_FRIENDLY = true;
    private static final LocalDate PREMISE_OFFER_1_FROM_DATE = LocalDate.now();
    private static final LocalDate PREMISE_OFFER_1_TO_DATE = PREMISE_OFFER_1_FROM_DATE.plus(1, ChronoUnit.MONTHS);
    private static final boolean PREMISE_OFFER_1_TO_ACTIVE = true;
    private static final boolean PREMISE_OFFER_1_TO_VERIFIED = true;
    private static final String PREMISE_OFFER_1_TO_DESCRIPTION = "description";

    private static PremiseOfferEntity PREMISE_OFFER_2;
    private static final Long PREMISE_OFFER_2_ID = 2L;
    private static final int PREMISE_OFFER_2_PEOPLE_COUNT = 3;
    private static final int PREMISE_OFFER_2_KITCHENS_COUNT = 2;
    private static final int PREMISE_OFFER_2_BEDROOMS_COUNT = 2;
    private static final int PREMISE_OFFER_2_BATHROOMS_COUNT = 2;
    private static final boolean PREMISE_OFFER_2_ANIMALS_ALLOWED = true;
    private static final boolean PREMISE_OFFER_2_WHEELCHAIR_FRIENDLY = false;
    private static final LocalDate PREMISE_OFFER_2_FROM_DATE = LocalDate.now().plus(1, ChronoUnit.DAYS);
    private static final LocalDate PREMISE_OFFER_2_TO_DATE = PREMISE_OFFER_1_FROM_DATE.plus(2, ChronoUnit.MONTHS);
    private static final boolean PREMISE_OFFER_2_TO_ACTIVE = true;
    private static final boolean PREMISE_OFFER_2_TO_VERIFIED = false;
    private static final String PREMISE_OFFER_2_TO_DESCRIPTION = "description";

    private static PremiseOfferEntity PREMISE_OFFER_3;
    private static final Long PREMISE_OFFER_3_ID = 3L;
    private static final int PREMISE_OFFER_3_PEOPLE_COUNT = 4;
    private static final int PREMISE_OFFER_3_KITCHENS_COUNT = 2;
    private static final int PREMISE_OFFER_3_BEDROOMS_COUNT = 2;
    private static final int PREMISE_OFFER_3_BATHROOMS_COUNT = 2;
    private static final boolean PREMISE_OFFER_3_ANIMALS_ALLOWED = true;
    private static final boolean PREMISE_OFFER_3_WHEELCHAIR_FRIENDLY = false;
    private static final LocalDate PREMISE_OFFER_3_FROM_DATE = LocalDate.now().plus(1, ChronoUnit.DAYS);
    private static final LocalDate PREMISE_OFFER_3_TO_DATE = PREMISE_OFFER_1_FROM_DATE.plus(2, ChronoUnit.MONTHS);
    private static final boolean PREMISE_OFFER_3_TO_ACTIVE = true;
    private static final boolean PREMISE_OFFER_3_TO_VERIFIED = false;
    private static final String PREMISE_OFFER_3_TO_DESCRIPTION = "description";

    private static HostEntity HOST_ENTITY_1;
    private static final Long HOST_1_ID = 1L;
    private static String HOST_1_EMAIL = "host1@help.ukraine.pl";

    private static HostEntity HOST_ENTITY_2;
    private static final Long HOST_2_ID = 2L;

    private static OfferImageEntity OFFER_IMAGE_1;
    private static final Long IMAGE_1_ID = 1L;
    private static final String IMAGE_1_LOCATION = "location1";

    private static OfferImageEntity OFFER_IMAGE_2;
    private static final Long IMAGE_2_ID = 2L;
    private static final String IMAGE_2_LOCATION = "location2";

    private static OfferImageEntity OFFER_IMAGE_3;
    private static final Long IMAGE_3_ID = 3L;
    private static final String IMAGE_3_LOCATION = "location3";

    private static final Long NOT_EXISTING_PREMISE_OFFER_ID = -1L;

    private static List<PremiseOfferEntity> allPremises;
    private static List<PremiseOfferEntity> premisesHost1;

    @BeforeEach
    void setUpMocks() {
        OFFER_IMAGE_1 = OfferImageEntity.builder()
                .id(IMAGE_1_ID)
                .imageLocation(IMAGE_1_LOCATION)
                .build();

        OFFER_IMAGE_2 = OfferImageEntity.builder()
                .id(IMAGE_2_ID)
                .imageLocation(IMAGE_2_LOCATION)
                .build();

        OFFER_IMAGE_3 = OfferImageEntity.builder()
                .id(IMAGE_3_ID)
                .imageLocation(IMAGE_3_LOCATION)
                .build();

        UserEntity userEntity = UserEntity
                .builder()
                .email(HOST_1_EMAIL)
                .build();

        HOST_ENTITY_1 = HostEntity.builder()
                .userId(HOST_1_ID)
                .user(userEntity)
                .build();
        HOST_ENTITY_2 = HostEntity.builder()
                .userId(HOST_2_ID)
                .build();

        PREMISE_OFFER_1 = PremiseOfferEntity.builder()
                .id(PREMISE_OFFER_1_ID)
                .host(HOST_ENTITY_1)
                .offerImages(List.of(OFFER_IMAGE_1))
                .peopleToTake(PREMISE_OFFER_1_PEOPLE_COUNT)
                .bathrooms(PREMISE_OFFER_1_BATHROOMS_COUNT)
                .kitchens(PREMISE_OFFER_1_KITCHENS_COUNT)
                .bedrooms(PREMISE_OFFER_1_BEDROOMS_COUNT)
                .animalsAllowed(PREMISE_OFFER_1_ANIMALS_ALLOWED)
                .wheelchairFriendly(PREMISE_OFFER_1_WHEELCHAIR_FRIENDLY)
                .fromDate(PREMISE_OFFER_1_FROM_DATE)
                .toDate(PREMISE_OFFER_1_TO_DATE)
                .active(PREMISE_OFFER_1_TO_ACTIVE)
                .verified(PREMISE_OFFER_1_TO_VERIFIED)
                .description(PREMISE_OFFER_1_TO_DESCRIPTION)
                .build();

        PREMISE_OFFER_2 = PremiseOfferEntity.builder()
                .id(PREMISE_OFFER_2_ID)
                .host(HOST_ENTITY_2)
                .offerImages(List.of(OFFER_IMAGE_2))
                .peopleToTake(PREMISE_OFFER_2_PEOPLE_COUNT)
                .bathrooms(PREMISE_OFFER_2_BATHROOMS_COUNT)
                .kitchens(PREMISE_OFFER_2_KITCHENS_COUNT)
                .bedrooms(PREMISE_OFFER_2_BEDROOMS_COUNT)
                .animalsAllowed(PREMISE_OFFER_2_ANIMALS_ALLOWED)
                .wheelchairFriendly(PREMISE_OFFER_2_WHEELCHAIR_FRIENDLY)
                .fromDate(PREMISE_OFFER_2_FROM_DATE)
                .toDate(PREMISE_OFFER_2_TO_DATE)
                .active(PREMISE_OFFER_2_TO_ACTIVE)
                .verified(PREMISE_OFFER_2_TO_VERIFIED)
                .description(PREMISE_OFFER_2_TO_DESCRIPTION)
                .build();

        PREMISE_OFFER_3 = PremiseOfferEntity.builder()
                .id(PREMISE_OFFER_3_ID)
                .host(HOST_ENTITY_1)
                .offerImages(List.of(OFFER_IMAGE_3))
//                .premiseAddress(PREMISE_OFFER_3_ADDRESS)
                .peopleToTake(PREMISE_OFFER_3_PEOPLE_COUNT)
                .bathrooms(PREMISE_OFFER_3_BATHROOMS_COUNT)
                .kitchens(PREMISE_OFFER_3_KITCHENS_COUNT)
                .bedrooms(PREMISE_OFFER_3_BEDROOMS_COUNT)
                .animalsAllowed(PREMISE_OFFER_3_ANIMALS_ALLOWED)
                .wheelchairFriendly(PREMISE_OFFER_3_WHEELCHAIR_FRIENDLY)
                .fromDate(PREMISE_OFFER_3_FROM_DATE)
                .toDate(PREMISE_OFFER_3_TO_DATE)
                .active(PREMISE_OFFER_3_TO_ACTIVE)
                .verified(PREMISE_OFFER_3_TO_VERIFIED)
                .description(PREMISE_OFFER_3_TO_DESCRIPTION)
                .build();

        allPremises = List.of(PREMISE_OFFER_1, PREMISE_OFFER_2, PREMISE_OFFER_3);
        premisesHost1 = List.of(PREMISE_OFFER_1, PREMISE_OFFER_3);

        doReturn(Optional.of(PREMISE_OFFER_1)).when(premiseOfferRepository).findById(PREMISE_OFFER_1.getId());
        doReturn(Optional.empty()).when(premiseOfferRepository).findById(NOT_EXISTING_PREMISE_OFFER_ID);
        doReturn(allPremises).when(premiseOfferRepository).findAll();
        doReturn(premisesHost1).when(premiseOfferRepository).findByHostId(HOST_1_ID);
        doReturn(premisesHost1).when(premiseOfferRepository).findByHostUserEmail(HOST_1_EMAIL);
    }

    @Test
    @Transactional
    void getPremiseByIdTest() throws Exception {
        PremiseOfferModel premiseOfferModel = premiseOfferService.getPremiseOfferById(PREMISE_OFFER_1_ID);
        compareEntityToModel(PREMISE_OFFER_1, premiseOfferModel);
    }

    @Test
    @Transactional
    void getPremiseByNotExistingIdTest() throws Exception {
        assertThrows(PremiseOfferNotFoundException.class, () -> premiseOfferService.getPremiseOfferById(NOT_EXISTING_PREMISE_OFFER_ID));
    }

    @Test
    @Transactional
    void getAllPremisesTest() throws Exception {
        List<PremiseOfferModel> allPremiseModels = premiseOfferService.getAllPremiseOffers();
        assertEquals(allPremises.size(), allPremiseModels.size());
        for (PremiseOfferEntity premiseOfferEntity : allPremises) {
            PremiseOfferModel premiseOfferModel = getPremiseModelById(allPremiseModels, premiseOfferEntity.getId());
            compareEntityToModel(premiseOfferEntity, premiseOfferModel);
        }
    }

    @Test
    @Transactional
    void getAllPremisesForHost1Test() throws Exception {
        List<PremiseOfferModel> premisesForHost1Models = premiseOfferService.getAllPremiseOffersByHostId(HOST_1_ID);
        assertEquals(premisesHost1.size(), premisesForHost1Models.size());
        for (PremiseOfferEntity premiseOfferEntity : premisesHost1) {
            PremiseOfferModel premiseOfferModel = getPremiseModelById(premisesForHost1Models, premiseOfferEntity.getId());
            compareEntityToModel(premiseOfferEntity, premiseOfferModel);
        }
    }

    @Test
    @Transactional
    void getAllPremisesForHost1ByEmailTest() throws Exception {
        List<PremiseOfferModel> premiseOfferModels = premiseOfferService.getAllPremiseOffersByHostEmail(HOST_1_EMAIL);
        assertEquals(premiseOfferModels.size(), premisesHost1.size());
        for (PremiseOfferEntity premiseOfferEntity : premisesHost1) {
            PremiseOfferModel premiseOfferModel = getPremiseModelById(premiseOfferModels, premiseOfferEntity.getId());
            compareEntityToModel(premiseOfferEntity, premiseOfferModel);
        }
    }

    @Test
    @Transactional
    void deleteExistingPremiseOfferTest() throws Exception {
        assertDoesNotThrow(() -> premiseOfferService.deletePremiseOfferById(PREMISE_OFFER_1_ID));
    }

    @Test
    @Transactional
    void deleteByNotExistingOfferTest() throws Exception {
        assertThrows(PremiseOfferNotFoundException.class, () -> premiseOfferService.deletePremiseOfferById(NOT_EXISTING_PREMISE_OFFER_ID));
    }

    private void compareEntityToModel(PremiseOfferEntity entity, PremiseOfferModel model) {
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getHost().getUserId(), model.getHostId());
        List<String> entityImageLocations = entity.getOfferImages().stream().map(OfferImageEntity::getImageLocation).collect(Collectors.toList());
        assertFalse(Collections.disjoint(entityImageLocations, model.getOfferImagesLocations()));
        assertEquals(entity.getBathrooms(), model.getBathrooms());
        assertEquals(entity.getKitchens(), model.getKitchens());
        assertEquals(entity.getBedrooms(), model.getBedrooms());
        assertEquals(entity.isAnimalsAllowed(), model.isAnimalsAllowed());
        assertEquals(entity.isWheelchairFriendly(), model.isWheelchairFriendly());
        assertEquals(entity.isActive(), model.isActive());
        assertEquals(entity.isVerified(), model.isVerified());
        assertEquals(entity.getDescription(), model.getDescription());
        assertEquals(entity.getFromDate(), model.getFromDate());
        assertEquals(entity.getToDate(), model.getToDate());
    }

    private PremiseOfferModel getPremiseModelById(List<PremiseOfferModel> premiseOfferModels, Long id) {
        return premiseOfferModels.stream().filter(premiseOfferModel -> premiseOfferModel.getId().equals(id)).findFirst().get();
    }
}
