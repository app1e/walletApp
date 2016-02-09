package services;

import com.google.inject.ImplementedBy;
import models.user.ApplicationProfile;
import services.impl.ProfileServiceImpl;

/**
 * Created by aivlev on 2/3/16.
 */
@ImplementedBy(ProfileServiceImpl.class)
public interface ProfileService {

    ApplicationProfile getApplicationProfile(String email);
}
