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
            "Congratulations, %s has accepted your searching offer!";

    private static final String ACCEPTED_MSG_SUBJECT = "%s has accepted your searching offer!";

    private static final String REJECTED_MSG = "Dear %s!\n" +
            "%s has rejected your searching offer, good luck next time!";

    private static final String REJECTED_MSG_SUBJECT = "%s has rejected your searching offer";

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
        mimeMessageHelper.setSubject(String.format(ACCEPTED_MSG_SUBJECT, host.getName()));
        mimeMessageHelper.setText(String.format(ACCEPTED_MSG, refugee.getName(), host.getName()), false);
        javaMailSender.send(mimeMessage);
    }

    public void sendRejectedNotificationMail(UserModel host, UserModel refugee) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(host.getEmail());
        mimeMessageHelper.setSubject(String.format(REJECTED_MSG_SUBJECT, host.getName()));
        mimeMessageHelper.setText(String.format(REJECTED_MSG, refugee.getName(), host.getName()), false);
        javaMailSender.send(mimeMessage);
    }

    private String parseAddress(PremiseOfferModel premiseOfferModel) {
        return premiseOfferModel.getCity() + ", " + premiseOfferModel.getStreet() + " "
                + premiseOfferModel.getHouseNumber() + ", "
                + premiseOfferModel.getPostalCode();
    }


}
