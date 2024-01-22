package dev.ozkan.labreport.model.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ozkan.labreport.model.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "report")
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "patient_id_no",length = 11)
    private String patientIdNumber;


    @Column(name = "diagnosis_header")
    private String diagnosisHeader;

    @Column(name = "diagnosis_content", columnDefinition = "Text")
    private String diagnosisContent;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "report_image",columnDefinition = "LONGBLOB")
    private byte[] reportImage;

    @ManyToOne
    @JoinColumn(name = "report_writer_id",nullable = false)
    @JsonIgnore
    private User reportWriter;

    public String getReportId() {
        return reportId;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPatientIdNumber() {
        return patientIdNumber;
    }

    public String getDiagnosisHeader() {
        return diagnosisHeader;
    }

    public String getDiagnosisContent() {
        return diagnosisContent;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public byte[] getReportImage() {
        return reportImage;
    }

    public User getReportWriter() {
        return reportWriter;
    }

    public static class Builder {
        private final MedicalReport reportToBuild;

        public Builder() {
            reportToBuild = new MedicalReport();
        }

        public Builder reportId(String reportId){
            this.reportToBuild.reportId = reportId;
            return this;
        }

        public Builder fileNumber(String fileNumber){
            this.reportToBuild.fileNumber = fileNumber;
            return this;
        }

        public Builder fullName(String fullName) {
            this.reportToBuild.fullName = fullName;
            return this;
        }

        public Builder patientIdNumber(String patientIdNumber) {
            this.reportToBuild.patientIdNumber = patientIdNumber;
            return this;
        }

        public Builder diagnosisHeader(String diagnosisHeader) {
            this.reportToBuild.diagnosisHeader = diagnosisHeader;
            return this;
        }

        public Builder diagnosisContent(String diagnosisContent) {
            this.reportToBuild.diagnosisContent = diagnosisContent;
            return this;
        }

        public Builder reportDate(LocalDate reportDate) {
            this.reportToBuild.reportDate = reportDate;
            return this;
        }

        public Builder reportImage(byte[] reportImage) {
            this.reportToBuild.reportImage = reportImage;
            return this;
        }

        public Builder reportWriter(User reportWriter){
            this.reportToBuild.reportWriter = reportWriter;
            return this;
        }

        public MedicalReport build() {
            return reportToBuild;
        }
    }

}
