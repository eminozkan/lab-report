package dev.ozkan.labreport.services.report.requests;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class UpdateMedicalReportServiceRequest {
    private String fileNumber;
    private String patientIdNumber;
    private String fullName;
    private String diagnosisHeader;
    private String diagnosisContent;
    private LocalDate reportDate;
    private MultipartFile reportImage;


    public String getFileNumber() {
        return fileNumber;
    }

    public UpdateMedicalReportServiceRequest setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
        return this;
    }

    public String getPatientIdNumber() {
        return patientIdNumber;
    }

    public UpdateMedicalReportServiceRequest setPatientIdNumber(String patientIdNumber) {
        this.patientIdNumber = patientIdNumber;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UpdateMedicalReportServiceRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDiagnosisHeader() {
        return diagnosisHeader;
    }

    public UpdateMedicalReportServiceRequest setDiagnosisHeader(String diagnosisHeader) {
        this.diagnosisHeader = diagnosisHeader;
        return this;
    }

    public String getDiagnosisContent() {
        return diagnosisContent;
    }

    public UpdateMedicalReportServiceRequest setDiagnosisContent(String diagnosisContent) {
        this.diagnosisContent = diagnosisContent;
        return this;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public UpdateMedicalReportServiceRequest setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public MultipartFile getReportImage() {
        return reportImage;
    }

    public UpdateMedicalReportServiceRequest setReportImage(MultipartFile reportImage) {
        this.reportImage = reportImage;
        return this;
    }
}
