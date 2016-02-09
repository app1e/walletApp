package services.impl;

import com.google.inject.Singleton;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import models.user.ApplicationProfile;
import play.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;
import services.MailService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * Created by aivlev on 2/2/16.
 */
@Singleton
public class MailServiceImpl implements MailService {

    private static final String MAIL_SUBJECT = "Wallet App activation";
    private static final String MSG_FROM = "From <" + Play.application().configuration().getString("smtp.user") + ">";
    private static final String ACTIVATION_SUBJECT = Play.application().configuration().getString("app.activation.subject");
    private static final String SECRET_KEY = Play.application().configuration().getString("app.secret.key");
    private static final long EXPIRED_DELTA_TIME = 3600000l;





    public void sendMail(ApplicationProfile applicationProfile) {

        String code = generateActivationCode(applicationProfile);

        final Email mail = new Email();
        mail.setSubject(MAIL_SUBJECT);
        mail.setFrom(MSG_FROM);
        mail.addTo("To <" + applicationProfile.getEmail() + ">");
        mail.setBodyHtml("<html><body><p>You received this email because you began use \"Wallet application\". " +
                "For further use please activate your account by clicking on the link below <br />" +
                "<a href='http://localhost:9000/activate?code=" + code + "'>activate your profile</a></p></body></html>");
        MailerPlugin.send(mail);
    }

    private String generateActivationCode(ApplicationProfile applicationProfile){

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date();

        byte[] secretKeyBytes = TextCodec.BASE64.encode(SECRET_KEY).getBytes();
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(applicationProfile.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRED_DELTA_TIME))
                .setSubject(ACTIVATION_SUBJECT)
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        String code = builder.compact();
        return code;
    }
}
