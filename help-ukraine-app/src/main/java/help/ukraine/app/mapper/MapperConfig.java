package help.ukraine.app.mapper;

import help.ukraine.app.data.UserEntity;
import help.ukraine.app.model.UserModel;
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
}
