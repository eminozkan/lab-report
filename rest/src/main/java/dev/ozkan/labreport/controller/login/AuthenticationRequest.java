package dev.ozkan.labreport.controller.login;

import dev.ozkan.labreport.services.auth.AuthenticationServiceRequest;

public record AuthenticationRequest(
        String hospitalIdNumber,
        String password
) {
    AuthenticationServiceRequest toServiceRequest(){
        return new AuthenticationServiceRequest()
                .setHospitalIdNumber(hospitalIdNumber)
                .setPassword(password);
    }
}
