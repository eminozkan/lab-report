package dev.ozkan.labreport.controller.patient;

import dev.ozkan.labreport.services.report.MedicalReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final MedicalReportService reportService;

    public PatientController(MedicalReportService reportService) {
        this.reportService = reportService;
    }


    @PostMapping
    ResponseEntity<?> getReportsByPatientId(@RequestBody GetReportsByPatientIdRequest request) {
        var reports = reportService.listReportsByPatientId(request.patientId());
        if (reports.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "No reports found matching the provided patient ID."));
        }
        return ResponseEntity.ok(reports);
    }

}
