package help.ukraine.app.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public MapperFacade userMapperFacade() {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }
}
