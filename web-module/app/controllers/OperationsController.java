package controllers;

import org.pac4j.play.java.JavaController;
import org.pac4j.play.java.RequiresAuthentication;
import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Result;
import play.mvc.With;
import services.MenuService;

/**
 * Created by aivlev on 1/26/16.
 */
@With(MenuService.class)
public class OperationsController extends JavaController {
    private static final String GOOGLE_CLIENT_NAME = "Google2Client";
    private static final String RECHARGE_TITLE = "Recharge Balance";
    private static final String EXPENSES_TITLE = "Expenses";

    @RequiresAuthentication(clientName = GOOGLE_CLIENT_NAME)
    public static Result recharge() {


        boolean ex = userExists().get(1000l);



        return ok(views.html.recharge.render(RECHARGE_TITLE, getUserProfile(), ex));
    }




    private static F.Promise<Boolean> userExists() {
        F.Promise<Boolean> resultPromise = WS.url("http://localhost:8080/wallet/api/user?email=test@test.ru").get().map(
                response ->  Boolean.valueOf(response.getBody())
        );
        return resultPromise;
    }

    @RequiresAuthentication(clientName = GOOGLE_CLIENT_NAME)
    public static  Result expenses() {
        return ok(views.html.expenses.render(EXPENSES_TITLE, getUserProfile()));
    }

}
