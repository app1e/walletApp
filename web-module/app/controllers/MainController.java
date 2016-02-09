package controllers;

import com.google.inject.Inject;
import models.user.ApplicationProfile;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.JavaController;
import play.mvc.Result;
import play.mvc.With;
import services.MailService;
import services.MenuService;
import services.ProfileService;

/**
 * Created by aivlev on 12/18/15.
 */
@With(MenuService.class)
public class MainController extends JavaController {

    private static final String GOOGLE_CLIENT_NAME = "Google2Client";
    private static final String TARGET_URL = "/";

    @Inject
    MailService mailService;

    @Inject
    ProfileService profileService;

    public Result index() throws TechnicalException {
        final CommonProfile profile = getUserProfile();
        final String loginUrl = getRedirectAction(GOOGLE_CLIENT_NAME, TARGET_URL).getLocation();
        ApplicationProfile applicationProfile = null;
        if(null != profile){
            applicationProfile = profileService.getApplicationProfile(profile.getEmail());
            if(null != applicationProfile){
                mailService.sendMail(applicationProfile);
            }
        }
        return ok(views.html.index.render(profile, loginUrl, applicationProfile));
    }
}
