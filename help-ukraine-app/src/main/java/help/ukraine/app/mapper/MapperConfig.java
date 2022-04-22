package help.ukraine.app.mapper;

import help.ukraine.app.data.*;
import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.SearchingOfferModel;
import help.ukraine.app.model.SearchingPersonModel;
import help.ukraine.app.model.UserModel;
import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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
}
