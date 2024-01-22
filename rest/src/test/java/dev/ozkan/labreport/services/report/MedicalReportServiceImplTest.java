package dev.ozkan.labreport.services.report;

import dev.ozkan.labreport.model.report.MedicalReport;
import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.repository.MedicalReportRepository;
import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.services.logger.Logger;
import dev.ozkan.labreport.services.report.requests.SaveMedicalReportServiceRequest;
import dev.ozkan.labreport.services.report.requests.UpdateMedicalReportServiceRequest;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MedicalReportServiceImplTest {

    @Mock
    MedicalReportRepository repository;

    @Mock
    UserRepository userRepository;

    @Mock
    Logger logger;

    MedicalReportServiceImpl medicalReportService;

    String reportWriterId;



    @BeforeEach
    void setUp(){
        medicalReportService = new MedicalReportServiceImpl(repository, userRepository, logger);
        reportWriterId = "0123456";


    }

    @Nested
    class SaveMedicalReports{
        SaveMedicalReportServiceRequest serviceRequest;

        @BeforeEach
        void setUp(){
            serviceRequest = new SaveMedicalReportServiceRequest()
                    .setFileNumber("532321")
                    .setPatientIdNumber("28584376014")
                    .setFullName("John Doe")
                    .setReportDate(LocalDate.now().minusDays(2))
                    .setDiagnosisHeader("Diagnosis Header")
                    .setDiagnosisContent("Content")
                    .setReportImage(new MockMultipartFile("file","file.png",MediaType.IMAGE_PNG_VALUE,"reportImage".getBytes()));
        }
        @Captor
        private ArgumentCaptor<MedicalReport> reportArgumentCaptor;

        @DisplayName("Invalid Report Date")
        @Test
        void invalidReportDate() throws IOException {
            serviceRequest.setReportDate(LocalDate.now().plusDays(2));

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED,result.getReason());
        }

        @DisplayName("Invalid size of patient Id Number")
        @Test
        void invalidSizeOfPatientIdNumber() throws IOException {
            serviceRequest.setPatientIdNumber("100000000001");

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED,result.getReason());
        }

        @DisplayName("Invalid type patient Id Number")
        @Test
        void invalidTypeOfPatientIdNumber() throws IOException {
            serviceRequest.setPatientIdNumber("10000ab0000");

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED,result.getReason());
        }

        @DisplayName("Invalid patient Id Number")
        @Test
        void invalidPatientIdNumber() throws IOException {
            serviceRequest.setPatientIdNumber("10000000000");

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED,result.getReason());
        }

        @DisplayName("Invalid report writer id")
        @Test
        void invalidReportWriterId() throws IOException {

            Mockito.doReturn(Optional.empty())
                    .when(userRepository)
                    .getByUserId(reportWriterId);

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.NOT_FOUND,result.getReason());
            Mockito.verify(logger)
                    .debug(Mockito.anyString());
        }

        @DisplayName("Success")
        @Test
        void success() throws IOException {
            var user = new User.Builder()
                    .userId(reportWriterId)
                    .build();
            Mockito.doReturn(Optional.of(user))
                    .when(userRepository)
                    .getByUserId(reportWriterId);

            final var result = medicalReportService.saveMedicalReport(serviceRequest,reportWriterId);

            assertTrue(result.isSuccess());
            Mockito.verify(repository)
                    .save(reportArgumentCaptor.capture());

            MedicalReport capturedReport = reportArgumentCaptor.getValue();
            assertEquals(serviceRequest.getFileNumber(),capturedReport.getFileNumber());
            assertEquals(serviceRequest.getFullName(),capturedReport.getFullName());
            assertEquals(serviceRequest.getPatientIdNumber(),capturedReport.getPatientIdNumber());
            assertEquals(serviceRequest.getDiagnosisHeader(),capturedReport.getDiagnosisHeader());
            assertEquals(serviceRequest.getDiagnosisContent(),capturedReport.getDiagnosisContent());
            assertEquals(serviceRequest.getReportDate(),capturedReport.getReportDate());
            assertEquals(reportWriterId,capturedReport.getReportWriter().getUserId());
        }
    }

    @Nested
    class UpdateMedicalReports{

        UpdateMedicalReportServiceRequest serviceRequest;

        String reportId;
        @BeforeEach
        void setUp(){
            reportId = "report-id";
            serviceRequest = new UpdateMedicalReportServiceRequest()
                    .setFullName("John Doe")
                    .setReportDate(LocalDate.now().minusDays(2))
                    .setDiagnosisHeader("Diagnosis Header")
                    .setDiagnosisContent("Content")
                    .setReportImage(new MockMultipartFile("file","file.png",MediaType.IMAGE_PNG_VALUE,"reportImage".getBytes()));
        }
        @DisplayName("Invalid Report Id")
        @Test
        void invalidReportId() throws IOException {
            Mockito.doReturn(Optional.empty())
                    .when(repository)
                    .getByReportId(reportId);

            var result = medicalReportService.updateMedicalReport(reportId,serviceRequest,reportWriterId);
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.NOT_FOUND,result.getReason());
        }

        @DisplayName("Success")
        @Test
        void invalidRequest() throws Exception{
            var report = new MedicalReport();
            Mockito.doReturn(Optional.of(report))
                    .when(repository)
                    .getByReportId(reportId);

            var result = medicalReportService.updateMedicalReport(reportId,serviceRequest,reportWriterId);
            assertTrue(result.isSuccess());

            Mockito.verify(repository)
                    .save(Mockito.any());
        }
    }
}