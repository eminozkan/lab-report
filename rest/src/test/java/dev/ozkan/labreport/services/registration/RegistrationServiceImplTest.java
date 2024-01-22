package dev.ozkan.labreport.services.registration;

import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    RegistrationServiceImpl registrationService;
    RegistrationServiceRequest request;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl(userRepository, passwordEncoder);
        request = new RegistrationServiceRequest()
                .setHospitalIdNumber("0123456")
                .setFullName("John Doe")
                .setPassword("password");
    }

    @DisplayName("Invalid length of Hospital ID Number By 6 digits")
    @Test
    void sixDigits() {
        request.hospitalIdNumber = "012345";

        final var result = registrationService.register(request);

        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
    }

    @DisplayName("Invalid length of Hospital ID Number By 8 digits")
    @Test
    void eightDigits() {
        request.hospitalIdNumber = "01234567";

        final var result = registrationService.register(request);

        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
    }

    @DisplayName("Invalid types of Hospital ID Number")
    @Test
    void invalidTypes() {
        List<String> invalidTypes = List.of(
                "0x12345",
                "[.12345",
                "[[.c123",
                "abcdefg",
                "$xtz123"
                );
        for (String s : invalidTypes) {
            request.hospitalIdNumber = s;

            final var result = registrationService.register(request);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
        }
    }

    @DisplayName("Already registered user")
    @Test
    void alreadyRegisteredUser(){

        Mockito.doReturn(Optional.of(new User()))
                .when(userRepository)
                .getUserByHospitalIdNumber(request.hospitalIdNumber);

        final var result = registrationService.register(request);



        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.CONFLICT,result.getReason());
    }

    @DisplayName("Success")
    @Test
    void success(){
        Mockito.doReturn(Optional.empty())
                .when(userRepository)
                .getUserByHospitalIdNumber(request.hospitalIdNumber);

        Mockito.doReturn("hashedPassword")
                .when(passwordEncoder)
                .encode(request.password);

        final var result = registrationService.register(request);

        Mockito.verify(userRepository)
                .save(userArgumentCaptor.capture());

        final var capturedUser = userArgumentCaptor.getValue();

        assertTrue(result.isSuccess());
        assertEquals(request.hospitalIdNumber,capturedUser.getHospitalIdNumber());
        assertEquals(request.fullName,capturedUser.getFullName());
    }

}