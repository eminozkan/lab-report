package dev.ozkan.labreport.controller.report;

import dev.ozkan.labreport.services.report.requests.UpdateMedicalReportServiceRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record UpdateMedicalReportRequest(
        String fileNumber,
        String patientIdNumber,
        String fullName,
        String diagnosisHeader,
        String diagnosisContent,
        String reportDate,
        MultipartFile reportImage

) {
    UpdateMedicalReportServiceRequest toServiceRequest() {
        LocalDate date = null;
        if (reportDate != null) {
            date = LocalDate.parse(reportDate);
        }
        return new UpdateMedicalReportServiceRequest()
                .setFullName(fullName)
                .setDiagnosisHeader(diagnosisHeader)
                .setDiagnosisContent(diagnosisContent)
                .setReportDate(date)
                .setReportImage(reportImage);
    }
}
