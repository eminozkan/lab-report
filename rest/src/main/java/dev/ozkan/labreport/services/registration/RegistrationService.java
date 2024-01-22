package dev.ozkan.labreport.services.registration;

import dev.ozkan.labreport.util.result.CrudResult;

public interface RegistrationService {

    CrudResult register(RegistrationServiceRequest request);
}
