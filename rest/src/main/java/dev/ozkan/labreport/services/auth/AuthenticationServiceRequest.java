package dev.ozkan.labreport.services.auth;

public class AuthenticationServiceRequest {
     String hospitalIdNumber;
     String password;

    public AuthenticationServiceRequest setHospitalIdNumber(String hospitalIdNumber) {
        this.hospitalIdNumber = hospitalIdNumber;
        return this;
    }

    public AuthenticationServiceRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
