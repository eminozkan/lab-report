package dev.ozkan.labreport.repository;

import dev.ozkan.labreport.model.report.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,String> {
    /**
     * Verilen Hasta TC kimlik numarasına, tanı başlığına ve rapor tarihi bilgilerine göre eşleşen bir rapor bulur.
     * @return Rapor, var ise bir Optional objesi içerisinde raporun kendisini, yok ise Optional.empty().
     */
    Optional<MedicalReport> findByPatientIdNumberAndDiagnosisHeaderAndReportDate(String patientIdNumber, String diagnosisHeader,LocalDate reportDate);

    /**
     * Verilen rapor id'si ile eşleşen bir rapor bulur.
     * @return Rapor, var ise bir Optional objesi içerisinde raporun kendisini, yok ise Optional.empty().
     */
    Optional<MedicalReport> getByReportId(String reportId);

    /**
     * Tüm raporları rapor tarihine göre azalan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByOrderByReportDateDesc();

    /**
     * Tüm raporları rapor tarihine göre artan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByOrderByReportDateAsc();

    /**
     * Tüm raporlar arasında hasta kimlik numarası ile eşleşenleri rapor tarihine göre artan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByPatientIdNumberContainingOrderByReportDateAsc(String search);

    /**
     * Tüm raporlar arasında hasta kimlik numarası ile eşleşenleri rapor tarihine göre azalan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByPatientIdNumberContainingOrderByReportDateDesc(String search);

    /**
     * Tüm raporlar arasında hasta adı ile eşleşenleri rapor tarihine göre azalan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByFullNameContainingOrderByReportDateDesc(String search);

    /**
     * Tüm raporlar arasında hasta adı ile eşleşenleri rapor tarihine göre artan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByFullNameContainingOrderByReportDateAsc(String search);

    /**
     * Tüm raporlar arasında raporu kayıt eden laborant adı ile eşleşenleri rapor tarihine göre azalan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByReportWriterFullNameContainingOrderByReportDateDesc(String search);

    /**
     * Tüm raporlar arasında raporu kayıt eden laborant adı ile eşleşenleri rapor tarihine göre artan sırada bulur.
     * @return Herhangi bir rapor bulunmuyor ise boş bir liste, var ise raporların tamamını döner.
     */
    List<MedicalReport> findAllByReportWriterFullNameContainingOrderByReportDateAsc(String search);

    /**
     * Tüm raporlar arasında verilen dosya numarası ile eşleşen raporu bulur.
     * @return Herhangi bir rapor bulunmuyor Optional.empty(), var ise raporun kendisini bir Optional objesi içerisinde raporun kendisini
     */
    Optional<MedicalReport> findByFileNumber(String fileNumber);
}
