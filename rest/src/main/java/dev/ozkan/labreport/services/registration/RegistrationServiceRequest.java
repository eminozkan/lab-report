package dev.ozkan.labreport.services.registration;

public class RegistrationServiceRequest {
    String hospitalIdNumber;
    String password;

    String fullName;
    public RegistrationServiceRequest setHospitalIdNumber(String hospitalIdNumber) {
        this.hospitalIdNumber = hospitalIdNumber;
        return this;
    }

    public RegistrationServiceRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegistrationServiceRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
