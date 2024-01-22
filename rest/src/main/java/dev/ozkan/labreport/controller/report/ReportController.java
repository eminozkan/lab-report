package dev.ozkan.labreport.controller.report;


import dev.ozkan.labreport.model.session.UserSession;
import dev.ozkan.labreport.services.report.MedicalReportService;
import dev.ozkan.labreport.util.result.handler.ResultHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final MedicalReportService reportService;

    public ReportController(MedicalReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> saveMedicalReport(@ModelAttribute SaveMedicalReportRequest request, UserSession session) throws IOException {
        var result = reportService.saveMedicalReport(request.toServiceRequest(),session.getUserId());
        if (!result.isSuccess()){
            return ResultHandler.handleFailureReason(result.getReason(),result.getMessage());
        }
        return ResponseEntity
                .status(201)
                .body("""
                        {"message" : "report saved successfully"}
                        """);
    }

    @PostMapping(path = "/{reportId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updateMedicalReport(@PathVariable String reportId, @ModelAttribute UpdateMedicalReportRequest request, UserSession session) throws IOException {
        var result = reportService.updateMedicalReport(reportId,request.toServiceRequest(), session.getUserId());
        if (!result.isSuccess()) {
            return ResultHandler.handleFailureReason(result.getReason(), result.getMessage());
        }
        return ResponseEntity
                .status(200)
                .body("""
                        {"message" : "report updated successfully"}
                        """);

    }

    @GetMapping("/{reportId}")
    ResponseEntity<?> getReportByReportId(@PathVariable String reportId){
        var report = reportService
                .getByReportId(reportId);
        if (report.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body("""
                            {"message" : "report not found with given id"}
                            """);
        }
        return ResponseEntity.ok(report.get());
    }

    @GetMapping("/desc")
    ResponseEntity<?> listReportsByDecreasingOrderDate(@RequestParam(required = false) String search){
        var reports = reportService.listReportsByDateOrder(search,true);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/asc")
    ResponseEntity<?> listReportsByAscendingOrderDate(@RequestParam(required = false) String search){
        var reports = reportService.listReportsByDateOrder(search,false);
        return ResponseEntity.ok(reports);
    }

    @DeleteMapping("/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReport(@PathVariable String reportId){
        reportService.deleteByReportId(reportId);
    }
}
