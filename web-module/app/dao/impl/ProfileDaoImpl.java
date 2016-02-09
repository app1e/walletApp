package dao.impl;

import com.google.inject.Singleton;
import dao.ProfileDao;
import models.user.ApplicationProfile;
import play.Play;
import play.libs.F;
import play.libs.ws.WS;

/**
 * Created by aivlev on 2/3/16.
 */
@Singleton
public class ProfileDaoImpl implements ProfileDao {

    private static final String REST_URL_PATH = Play.application().configuration().getString("rest.api.url");
    private static final String EXISTS_PATH = "/user?email=";
    private static final long TIMEOUT = 1000l;

    @Override
    public ApplicationProfile getApplicationProfile(String email) {
        ApplicationProfile applicationProfile = null;

        if(!profileExists(email).get(TIMEOUT)){
            applicationProfile = ApplicationProfile.find.where().like("email", email).findUnique();
            if(null == applicationProfile){
                applicationProfile = new ApplicationProfile(email, 0);
                applicationProfile.save();
            }
        }
        return applicationProfile;
    }

    private F.Promise<Boolean> profileExists(String email) {
        F.Promise<Boolean> resultPromise = WS.url(REST_URL_PATH + EXISTS_PATH + email).get().map(
                response ->  Boolean.valueOf(response.getBody())
        );
        return resultPromise;
    }


}
