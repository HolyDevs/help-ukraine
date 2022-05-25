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

    private static final String OFFER_MSG = "Dear %s!\n" +
            "%s is interested in your offer of accommodation located in %s! \uD83D\uDC40";

    private static final String OFFER_MSG_SUBJECT = "Someone is interested in your offer! \uD83D\uDC40";

    public void sendOfferNotificationMail(UserModel host, UserModel refugee, PremiseOfferModel premiseOfferModel) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(host.getEmail());
        mimeMessageHelper.setSubject(OFFER_MSG_SUBJECT);
        String offerAddress = parseAddress(premiseOfferModel);
        mimeMessageHelper.setText(String.format(OFFER_MSG, host.getName(), refugee.getName(), offerAddress), false);
        javaMailSender.send(mimeMessage);
    }

    private String parseAddress(PremiseOfferModel premiseOfferModel) {
        return premiseOfferModel.getCity() + ", " + premiseOfferModel.getStreet() + " "
                + premiseOfferModel.getHouseNumber() + ", "
                + premiseOfferModel.getPostalCode();
    }


}
