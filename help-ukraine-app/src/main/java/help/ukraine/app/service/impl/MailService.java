package help.ukraine.app.service.impl;

import help.ukraine.app.model.PremiseOfferModel;
import help.ukraine.app.model.UserModel;
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

    private static final String PENDING_MSG = "Dear %s!\n" +
            "%s is interested in your offer of accommodation located in %s! \uD83D\uDC40";

    private static final String PENDING_MSG_SUBJECT = "Someone is interested in your offer! \uD83D\uDC40";

    private static final String ACCEPTED_MSG = "Dear %s!\n" +
            "... %s ...";

    private static final String ACCEPTED_MSG_SUBJECT = "";

    public void sendPendingNotificationMail(UserModel host, UserModel refugee, PremiseOfferModel premiseOfferModel) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(host.getEmail());
        mimeMessageHelper.setSubject(PENDING_MSG_SUBJECT);
        String offerAddress = parseAddress(premiseOfferModel);
        mimeMessageHelper.setText(String.format(PENDING_MSG, host.getName(), refugee.getName(), offerAddress), false);
        javaMailSender.send(mimeMessage);
    }

    public void sendAcceptedNotificationMail(UserModel host, UserModel refugee) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(host.getEmail());
        mimeMessageHelper.setSubject(ACCEPTED_MSG_SUBJECT);
        mimeMessageHelper.setText(String.format(ACCEPTED_MSG, host.getName(), refugee.getName()), false);
        javaMailSender.send(mimeMessage);
    }

    private String parseAddress(PremiseOfferModel premiseOfferModel) {
        return premiseOfferModel.getCity() + ", " + premiseOfferModel.getStreet() + " "
                + premiseOfferModel.getHouseNumber() + ", "
                + premiseOfferModel.getPostalCode();
    }


}
