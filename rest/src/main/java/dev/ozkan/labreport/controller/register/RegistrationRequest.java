package dev.ozkan.labreport.controller.register;

import dev.ozkan.labreport.services.registration.RegistrationServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(

        @NotNull
        @NotEmpty
        String hospitalIdNumber,
        @NotEmpty
        @NotNull
        @Length(min = 6)
        String password,

        @NotNull
        @NotEmpty
        String fullName
) {
    RegistrationServiceRequest toServiceRequest() {
        return new RegistrationServiceRequest()
                .setFullName(fullName)
                .setHospitalIdNumber(hospitalIdNumber)
                .setPassword(password);
    }
}
