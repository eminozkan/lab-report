package dev.ozkan.labreport.services.report;

import dev.ozkan.labreport.model.report.MedicalReport;
import dev.ozkan.labreport.repository.MedicalReportRepository;
import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.services.logger.Logger;
import dev.ozkan.labreport.services.report.requests.SaveMedicalReportServiceRequest;
import dev.ozkan.labreport.services.report.requests.UpdateMedicalReportServiceRequest;
import dev.ozkan.labreport.util.result.CrudResult;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {

    private final MedicalReportRepository medicalReportRepository;

    private final UserRepository userRepository;

    private final Logger logger;

    public MedicalReportServiceImpl(MedicalReportRepository medicalReportRepository, UserRepository userRepository, Logger logger) {
        this.medicalReportRepository = medicalReportRepository;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    private static boolean isImageTypeSupported(MultipartFile imageFile) {
        return Objects.equals(imageFile.getContentType(), MediaType.IMAGE_JPEG_VALUE) ||
                Objects.equals(imageFile.getContentType(), MediaType.IMAGE_PNG_VALUE);
    }

    @Override
    public CrudResult saveMedicalReport(SaveMedicalReportServiceRequest request, String reportWriterId) throws IOException {
        var isRequestValid = isReportRequestValid(request);
        if (!isRequestValid.isValid) {
            return CrudResult.failed(OperationFailureReason.PRECONDITION_FAILED, isRequestValid.message);
        }

        var reportFromDb = medicalReportRepository.findByPatientIdNumberAndDiagnosisHeaderAndReportDate(request.getPatientIdNumber(), request.getDiagnosisHeader(), request.getReportDate());
        if (reportFromDb.isPresent()) {
            return CrudResult.failed(OperationFailureReason.CONFLICT, "Report has been saved already.");
        }
        var reportFromDbByFileNumber = medicalReportRepository.findByFileNumber(request.getFileNumber());
        if (reportFromDbByFileNumber.isPresent()){
            return CrudResult.failed(OperationFailureReason.CONFLICT, "Report has been saved already.");
        }
        var userOptional = userRepository.getByUserId(reportWriterId);

        if (userOptional.isEmpty()) {
            logger.debug("report writer not found");
            return CrudResult.failed(OperationFailureReason.NOT_FOUND, "Invalid Report Writer Id");
        }

        var report = new MedicalReport.Builder()
                .fileNumber(request.getFileNumber())
                .fullName(request.getFullName())
                .diagnosisContent(request.getDiagnosisContent())
                .patientIdNumber(request.getPatientIdNumber())
                .reportDate(request.getReportDate())
                .reportImage(request.getReportImage().getBytes())
                .diagnosisHeader(request.getDiagnosisHeader())
                .reportWriter(userOptional.get())
                .build();

        medicalReportRepository.save(report);
        return CrudResult.success();
    }

    @Override
    public CrudResult updateMedicalReport(String reportId, UpdateMedicalReportServiceRequest request, String reportUpdaterId) throws IOException {
        var reportOptional = medicalReportRepository.getByReportId(reportId);
        if (reportOptional.isEmpty()) {
            return CrudResult.failed(OperationFailureReason.NOT_FOUND, "Report not Found");
        }

        var reportFromDb = reportOptional.get();
        var updatedReport = changeReportFieldsWithNewValues(request, reportFromDb);
        medicalReportRepository.save(updatedReport);

        return CrudResult.success();
    }

    @Override
    public List<MedicalReport> listReportsByDateOrder(String search, boolean isDecreasing) {
        List<MedicalReport> reports;

        if (ObjectUtils.isEmpty(search)) {
            reports = getMedicalReports(isDecreasing);
        } else {
            if (isCharactersAreDigits(search)) {
                reports = getMedicalReportsByPatientId(search, isDecreasing);
            } else {
                reports = getMedicalReportsByPatientName(search, isDecreasing);

                if (reports.isEmpty()) {
                    reports = getMedicalReportsByTechnicianName(search, isDecreasing);
                }
            }
        }

        return reports;
    }

    private List<MedicalReport> getMedicalReportsByTechnicianName(String search, boolean isDecreasing) {
        List<MedicalReport> reports;
            if (isDecreasing) {
                reports = medicalReportRepository.findAllByReportWriterFullNameContainingOrderByReportDateDesc(search);
            } else {
                reports = medicalReportRepository.findAllByReportWriterFullNameContainingOrderByReportDateAsc(search);

            }
        return reports;
    }

    private List<MedicalReport> getMedicalReportsByPatientName(String search, boolean isDecreasing) {
        List<MedicalReport> reports;
        if (isDecreasing) {
            reports = medicalReportRepository.findAllByFullNameContainingOrderByReportDateDesc(search);
        } else {
            reports = medicalReportRepository.findAllByFullNameContainingOrderByReportDateAsc(search);
        }
        return reports;
    }

    private List<MedicalReport> getMedicalReportsByPatientId(String search, boolean isDecreasing) {
        List<MedicalReport> reports;
        if (isDecreasing) {
            reports = medicalReportRepository.findAllByPatientIdNumberContainingOrderByReportDateDesc(search);
        } else {
            reports = medicalReportRepository.findAllByPatientIdNumberContainingOrderByReportDateAsc(search);
        }
        return reports;
    }

    private List<MedicalReport> getMedicalReports(boolean isDecreasing) {
        List<MedicalReport> reports;
        if (isDecreasing) {
            reports = medicalReportRepository.findAllByOrderByReportDateDesc();
        } else {
            reports = medicalReportRepository.findAllByOrderByReportDateAsc();
        }
        return reports;
    }

    @Override
    public Optional<MedicalReport> getByReportId(String reportId) {
        return medicalReportRepository.getByReportId(reportId);
    }

    @Override
    public void deleteByReportId(String reportId) {
        medicalReportRepository.deleteById(reportId);
    }

    @Override
    public List<MedicalReport> listReportsByPatientId(String patientId) {
        List<MedicalReport> reports = medicalReportRepository.findAll();
        return reports.stream().filter(report -> report.getPatientIdNumber().equals(patientId)).toList();
    }

    private MedicalReport changeReportFieldsWithNewValues(UpdateMedicalReportServiceRequest request, MedicalReport reportFromDb) throws IOException {
        String fileNumber;
        if (ObjectUtils.isEmpty(request.getFileNumber())){
            fileNumber = reportFromDb.getFileNumber();
        }else{
            fileNumber = request.getFileNumber();
        }

        String patientId;
        if (!ObjectUtils.isEmpty(request.getPatientIdNumber()) && isValidIdNumber(request.getPatientIdNumber())){
            patientId = request.getPatientIdNumber();
        }else{
            patientId =  reportFromDb.getPatientIdNumber();
        }

        String fullName;
        if (ObjectUtils.isEmpty(request.getFullName())) {
            fullName = reportFromDb.getFullName();
        } else {
            fullName = request.getFullName();
        }

        String diagnosisHeader;
        if (ObjectUtils.isEmpty(request.getDiagnosisHeader())) {
            diagnosisHeader = reportFromDb.getDiagnosisHeader();
        } else {
            diagnosisHeader = request.getDiagnosisHeader();
        }

        String diagnosisContent;
        if (ObjectUtils.isEmpty(request.getDiagnosisContent())) {
            diagnosisContent = reportFromDb.getDiagnosisContent();
        } else {
            diagnosisContent = request.getDiagnosisContent();
        }

        LocalDate reportDate;
        if (request.getReportDate() == null) {
            reportDate = reportFromDb.getReportDate();
        } else {
            reportDate = request.getReportDate();
        }

        byte[] reportImage;
        if (request.getReportImage() == null || isImageTypeSupported(request.getReportImage())) {
            reportImage = reportFromDb.getReportImage();
        } else {
            reportImage = request.getReportImage().getBytes();
        }

        return new MedicalReport.Builder()
                .reportId(reportFromDb.getReportId())
                .fileNumber(fileNumber)
                .fullName(fullName)
                .patientIdNumber(patientId)
                .diagnosisHeader(diagnosisHeader)
                .diagnosisContent(diagnosisContent)
                .reportDate(reportDate)
                .reportImage(reportImage)
                .reportWriter(reportFromDb.getReportWriter())
                .build();

    }

    private NotValidReportRequest isReportRequestValid(SaveMedicalReportServiceRequest request) {
        if (request.getReportDate().isAfter(LocalDate.now())) {
            return new NotValidReportRequest()
                    .setValid(false)
                    .setMessage("invalid report date");
        }
        if (request.getPatientIdNumber().length() != 11) {
            return new NotValidReportRequest()
                    .setValid(false)
                    .setMessage("invalid patient id length");
        }
        if (!isCharactersAreDigits(request.getPatientIdNumber())) {
            return new NotValidReportRequest()
                    .setValid(false)
                    .setMessage("invalid type of patient id");
        }

        if (!isValidIdNumber(request.getPatientIdNumber())) {
            return new NotValidReportRequest()
                    .setValid(false)
                    .setMessage("invalid patient id");
        }

        if (request.getReportImage() == null || !isImageTypeSupported(request.getReportImage())) {
            return new NotValidReportRequest()
                    .setValid(false)
                    .setMessage("report image is empty or not supported type");
        }
        return new NotValidReportRequest()
                .setValid(true);
    }


    private boolean isCharactersAreDigits(String patientIdNumber) {
        for (int i = 0; i < patientIdNumber.length(); i++) {
            if (!Character.isDigit(patientIdNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidIdNumber(String patientIdNumber) {
        if (patientIdNumber.charAt(0) == '0') {
            return false;
        }
        int lastDigit = (patientIdNumber.charAt(patientIdNumber.length() - 1) - 48);
        if (patientIdNumber.charAt(patientIdNumber.length() - 1) % 2 != 0) {
            return false;
        }
        int sumOf10Digits = 0;
        for (int i = 0; i < 10; i++) {
            sumOf10Digits += (patientIdNumber.charAt(i) - 48);
        }

        return sumOf10Digits % 10 == lastDigit;
    }

    private static class NotValidReportRequest {
        boolean isValid;
        String message;

        public boolean isValid() {
            return isValid;
        }

        public NotValidReportRequest setValid(boolean valid) {
            isValid = valid;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public NotValidReportRequest setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
