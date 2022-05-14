package help.ukraine.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private static final String OFFER_MSG = "Dear %s!\n" +
            "%s is interested in your offer of accommodation located in %s! \uD83D\uDC40";

    private static final String OFFER_MSG_SUBJECT = "Someone is interested in your offer! \uD83D\uDC40";

    public void sendOfferNotificationMail(String hostMail, String hostName, String refugeeName, String address) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(hostMail);
        mimeMessageHelper.setSubject(OFFER_MSG_SUBJECT);
        mimeMessageHelper.setText(String.format(OFFER_MSG, hostName, refugeeName, address), false);
        javaMailSender.send(mimeMessage);
    }


}
