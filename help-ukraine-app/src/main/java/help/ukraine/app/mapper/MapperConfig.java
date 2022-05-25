package help.ukraine.app.mapper;

import help.ukraine.app.data.*;
import help.ukraine.app.model.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public MapperFacade userMapperFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(UserModel.class, UserEntity.class)
                .field("password", "hashedPassword").byDefault().register();
        return mapperFactory.getMapperFacade();
    }

    @Bean
    public MapperFacade premiseOfferFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(PremiseOfferModel.class, PremiseOfferEntity.class)
                .field("hostId", "host.userId")
                .field("offerImagesLocations{}", "offerImages{imageLocation}")
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }

    @Bean
    public MapperFacade searchingOfferFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(SearchingOfferModel.class, SearchingOfferEntity.class)
                .field("refugeeId", "refugee.userId")
                .byDefault()
                .register();

        mapperFactory.classMap(SearchingPersonModel.class, SearchingPersonEntity.class)
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }

    @Bean
    public MapperFacade pendingFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(PendingEntity.class, PendingModel.class)
                .field("searchingPremiseOfferId.searchingOffer.id", "searchingOfferId")
                .field("searchingPremiseOfferId.premiseOffer.id", "premiseOfferId")
                .byDefault()
                .register();
        return mapperFactory.getMapperFacade();
    }

    @Bean
    public MapperFacade acceptedFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(AcceptedEntity.class, AcceptedModel.class)
                .field("searchingPremiseOfferId.searchingOffer.id", "searchingOfferId")
                .field("searchingPremiseOfferId.premiseOffer.id", "premiseOfferId")
                .byDefault()
                .register();
        return mapperFactory.getMapperFacade();
    }
}
