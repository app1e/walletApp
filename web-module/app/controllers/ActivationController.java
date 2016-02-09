package controllers;

import com.google.inject.Inject;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.JavaController;
import org.pac4j.play.java.RequiresAuthentication;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.With;
import services.ActivationService;
import services.MenuService;

/**
 * Created by aivlev on 2/3/16.
 */
@With(MenuService.class)
public class ActivationController extends JavaController {

    private static final String GOOGLE_CLIENT_NAME = "Google2Client";
    private static final String INDEX = "/";

    @Inject
    ActivationService activationService;

    @RequiresAuthentication(clientName = GOOGLE_CLIENT_NAME)
    public Result activate(String code){
        final CommonProfile profile = getUserProfile();

        int status = activationService.activate(code, profile);
        switch(status) {
            case Http.Status.OK :
                return redirect(INDEX);
            case Http.Status.BAD_REQUEST:
                return badRequest(views.html.error400.render("Bad request", "Bad request", profile));
            case Http.Status.NOT_FOUND:
                return notFound(views.html.error404.render("Not Found", "Your profile has been activated", profile));
            case Http.Status.PRECONDITION_FAILED:
                return notFound(views.html.error412.render("Precondition Failed", "Your code was expired", profile));
            default:
                return internalServerError(views.html.error500.render("Internal Server Error", "Internal Server Error", profile));
        }
    }

}
