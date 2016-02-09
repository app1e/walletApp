package services;

import com.google.inject.ImplementedBy;
import org.pac4j.core.profile.CommonProfile;
import services.impl.ActivationServiceImpl;

/**
 * Created by aivlev on 2/3/16.
 */
@ImplementedBy(ActivationServiceImpl.class)
public interface ActivationService {
    int activate(String code, CommonProfile profile);

}
