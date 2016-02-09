package services.impl;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import models.user.ApplicationProfile;
import org.pac4j.core.profile.CommonProfile;
import play.Logger;
import play.Play;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import services.ActivationService;

/**
 * Created by aivlev on 2/3/16.
 */
@Singleton
public class ActivationServiceImpl implements ActivationService {

    private static final String REST_URL_PATH = Play.application().configuration().getString("rest.api.url");
    private static final String REQUEST_PATH = "/users";
    private static final long TIMEOUT = 1000l;
    private static final String ACTIVATION_SUBJECT = Play.application().configuration().getString("app.activation.subject");
    private static final String SECRET_KEY = Play.application().configuration().getString("app.secret.key");

    @Override
    public int activate(String code, CommonProfile profile) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.encode(SECRET_KEY).getBytes())
                    .parseClaimsJws(code).getBody();

            if(claims.getId().equals(profile.getEmail())
                    && claims.getSubject().equals(ACTIVATION_SUBJECT)){
                    ApplicationProfile applicationProfile = ApplicationProfile.find.where()
                            .eq("email", profile.getEmail())
                            .findUnique();
                    if(null != applicationProfile && applicationProfile.getActive() != 1){
                        JsonNode json = Json.newObject()
                                .put("email", profile.getEmail())
                                .put("fullName", profile.getDisplayName());
                        JsonNode jsonResult = addUser(json).get(TIMEOUT);
                        int status = jsonResult.findPath("status").asInt();
                        if(status == Http.Status.OK){
                            applicationProfile.setActive(1);
                            Ebean.update(applicationProfile);
                            return Http.Status.OK;
                        } else return status;
                    } else return Http.Status.NOT_FOUND;
            } else return Http.Status.BAD_REQUEST;
        } catch (ExpiredJwtException ex){
            Logger.error(ex.getClass() + " - " + ex.getMessage());
            return Http.Status.PRECONDITION_FAILED;
        }
        catch (SignatureException | MalformedJwtException ex){
            Logger.error(ex.getClass() + " - " + ex.getMessage());
            return Http.Status.BAD_REQUEST;
        }
        catch (JwtException ex){
            Logger.error(ex.getClass() + " - " + ex.getMessage());
            return Http.Status.INTERNAL_SERVER_ERROR;
        }
    }

    private F.Promise<JsonNode> addUser(JsonNode json) {
        F.Promise<JsonNode> jsonResult =  WS.url(REST_URL_PATH + REQUEST_PATH).
                setContentType("application/json; charset=utf-8")
                .post(json).map(
                    new F.Function<WSResponse, JsonNode>(){
                        @Override
                        public JsonNode apply(WSResponse response) throws Throwable {
                            JsonNode json = Json.newObject()
                                    .put("status", response.getStatus())
                                    .put("message", response.getStatusText());
                            return json;
                        }
                    }
                );
        return jsonResult;
    }
}
