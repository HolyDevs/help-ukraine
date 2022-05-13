package help.ukraine.app.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@Profile("local")
public class LocalConfig {
//    public JavaMailSender javaMailSender() {
//        return Mockito.mock(JavaMailSender.class);
//    }

}
