package dev.ozkan.labreport.services.report.requests;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class SaveMedicalReportServiceRequest {
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

    public SaveMedicalReportServiceRequest setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
        return this;
    }

    public String getPatientIdNumber() {
        return patientIdNumber;
    }

    public SaveMedicalReportServiceRequest setPatientIdNumber(String patientIdNumber) {
        this.patientIdNumber = patientIdNumber;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public SaveMedicalReportServiceRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDiagnosisHeader() {
        return diagnosisHeader;
    }

    public SaveMedicalReportServiceRequest setDiagnosisHeader(String diagnosisHeader) {
        this.diagnosisHeader = diagnosisHeader;
        return this;
    }

    public String getDiagnosisContent() {
        return diagnosisContent;
    }

    public SaveMedicalReportServiceRequest setDiagnosisContent(String diagnosisContent) {
        this.diagnosisContent = diagnosisContent;
        return this;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public SaveMedicalReportServiceRequest setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public MultipartFile getReportImage() {
        return reportImage;
    }

    public SaveMedicalReportServiceRequest setReportImage(MultipartFile reportImage) {
        this.reportImage = reportImage;
        return this;
    }
}
