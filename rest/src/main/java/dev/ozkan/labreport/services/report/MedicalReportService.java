package dev.ozkan.labreport.services.report;

import dev.ozkan.labreport.model.report.MedicalReport;
import dev.ozkan.labreport.services.report.requests.SaveMedicalReportServiceRequest;
import dev.ozkan.labreport.services.report.requests.UpdateMedicalReportServiceRequest;
import dev.ozkan.labreport.util.result.CrudResult;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MedicalReportService {
    CrudResult saveMedicalReport(SaveMedicalReportServiceRequest request, String reportWriterId) throws IOException;

    CrudResult updateMedicalReport(String reportId,UpdateMedicalReportServiceRequest request, String reportUpdaterId) throws IOException;

    List<MedicalReport> listReportsByDateOrder(String search,boolean isDecreasing);

    List<MedicalReport> listReportsByPatientId(String patientId);

    Optional<MedicalReport> getByReportId(String reportId);

    void deleteByReportId(String reportId);
}
