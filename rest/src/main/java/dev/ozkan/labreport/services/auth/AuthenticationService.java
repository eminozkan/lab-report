package dev.ozkan.labreport.services.auth;

import dev.ozkan.labreport.util.result.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(AuthenticationServiceRequest request);
}
