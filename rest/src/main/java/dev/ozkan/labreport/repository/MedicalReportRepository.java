package dev.ozkan.labreport.repository;

import dev.ozkan.labreport.model.report.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,String> {
    Optional<MedicalReport> findByPatientIdNumberAndDiagnosisHeaderAndReportDate(String patientIdNumber, String diagnosisHeader,LocalDate reportDate);

    Optional<MedicalReport> getByReportId(String reportId);

    List<MedicalReport> findAllByOrderByReportDateDesc();

    List<MedicalReport> findAllByOrderByReportDateAsc();

    List<MedicalReport> findAllByPatientIdNumberContainingOrderByReportDateAsc(String search);

    List<MedicalReport> findAllByPatientIdNumberContainingOrderByReportDateDesc(String search);

    List<MedicalReport> findAllByFullNameContainingOrderByReportDateDesc(String search);

    List<MedicalReport> findAllByFullNameContainingOrderByReportDateAsc(String search);

    List<MedicalReport> findAllByReportWriterFullNameContainingOrderByReportDateDesc(String search);

    List<MedicalReport> findAllByReportWriterFullNameContainingOrderByReportDateAsc(String search);

    Optional<MedicalReport> findByFileNumber(String fileNumber);
}
