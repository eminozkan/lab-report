package dev.ozkan.labreport.controller.report;

import dev.ozkan.labreport.services.report.requests.SaveMedicalReportServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record SaveMedicalReportRequest(
        @NotNull
        @NotBlank
        String fullName,
        @NotNull
        @NotBlank
        String fileNumber,
        @NotNull
        @NotBlank
        @Length(min = 11, max = 11)
        String patientIdNumber,
        @NotNull
        @NotBlank
        String diagnosisHeader,
        @NotNull
        @NotBlank
        String diagnosisContent,

        @NotNull
        @NotBlank
        String reportDate,

        @NotNull
        MultipartFile reportImage
) {

    SaveMedicalReportServiceRequest toServiceRequest() {
        return new SaveMedicalReportServiceRequest()
                .setReportDate(LocalDate.parse(reportDate))
                .setDiagnosisHeader(diagnosisHeader)
                .setFileNumber(fileNumber)
                .setReportImage(reportImage)
                .setFullName(fullName)
                .setDiagnosisContent(diagnosisContent)
                .setPatientIdNumber(patientIdNumber);
    }
}
