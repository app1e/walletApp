package global;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.ActivationController;
import controllers.MainController;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.play.Config;
import play.Application;
import play.GlobalSettings;
import play.Play;
import play.libs.F;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.impl.MailServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;

/**
 * Created by aivlev on 12/15/15.
 */
public class Global extends GlobalSettings {

    private static final Injector INJECTOR = createInjector();

    @Override
    public void onStart(final Application app) {
        Config.setErrorPage401(views.html.error401.render().toString());
        Config.setErrorPage403(views.html.error403.render().toString());
        Config.setProfileTimeout(900);


        final String gId = Play.application().configuration().getString("google.oauth2.clientId");
        final String gSecret = Play.application().configuration().getString("google.oauth2.clientSecret");


        Google2Client oidcClient = new Google2Client(gId, gSecret);
        oidcClient.setScope(Google2Client.Google2Scope.EMAIL);
        List<Client> list = new ArrayList<Client>();
        list.add(oidcClient);


        final Clients clients = new Clients("http://localhost:9000/callback", list);
        Config.setClients(clients);

    }

    private static Injector createInjector() {
        return Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                requestStaticInjection(MainController.class, ActivationController.class);
            }
        });
    }


    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return INJECTOR.getInstance(controllerClass);
    }


}