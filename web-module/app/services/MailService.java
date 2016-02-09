package services;

import com.google.inject.ImplementedBy;
import models.user.ApplicationProfile;
import services.impl.MailServiceImpl;

/**
 * Created by aivlev on 2/2/16.
 */
@ImplementedBy(MailServiceImpl.class)
public interface MailService {

    void sendMail(ApplicationProfile applicationProfile);

}
