package dev.ozkan.labreport.controller.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetReportsByPatientIdRequest(
        @NotNull
        @NotBlank
        String patientId
) {
}
