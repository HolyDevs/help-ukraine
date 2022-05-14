package help.ukraine.app.config;

import help.ukraine.app.service.MailService;
import org.mockito.Mockito;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Configuration
@Profile({"local", "intergrationtest", "unittest"})
public class LocalConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        JavaMailSender javaMailSender = Mockito.mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        return javaMailSender;
    }
}
