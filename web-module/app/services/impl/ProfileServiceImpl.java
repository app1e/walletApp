package services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.ProfileDao;
import models.user.ApplicationProfile;
import services.ProfileService;

/**
 * Created by aivlev on 2/3/16.
 */
@Singleton
public class ProfileServiceImpl implements ProfileService {


    @Inject
    ProfileDao profileDao;

    @Override
    public ApplicationProfile getApplicationProfile(String email) {
        return profileDao.getApplicationProfile(email);
    }
}
