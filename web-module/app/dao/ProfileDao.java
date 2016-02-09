package dao;

import com.google.inject.ImplementedBy;
import dao.impl.ProfileDaoImpl;
import models.user.ApplicationProfile;

/**
 * Created by aivlev on 2/3/16.
 */
@ImplementedBy(ProfileDaoImpl.class)
public interface ProfileDao {
    ApplicationProfile getApplicationProfile(String email);
}
