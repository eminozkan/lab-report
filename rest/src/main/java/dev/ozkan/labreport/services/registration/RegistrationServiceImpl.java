package dev.ozkan.labreport.services.registration;

import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.model.user.UserRole;
import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.util.result.CrudResult;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CrudResult register(RegistrationServiceRequest request) {
        if (request.hospitalIdNumber.length() != 7) {
            return CrudResult.failed(OperationFailureReason.PRECONDITION_FAILED, "Invalid Hospital Id Length");
        }
        if (isHospitalIdContainsAnyCharacterOtherThanNumericCharacters(request.hospitalIdNumber)) {
            return CrudResult.failed(OperationFailureReason.PRECONDITION_FAILED, "Invalid Type of Hospital ID Number");
        }

        final var userOptional = userRepository.getUserByHospitalIdNumber(request.hospitalIdNumber);

        if (userOptional.isPresent()){
            return CrudResult.failed(OperationFailureReason.CONFLICT, "User already registered");
        }

        final var user = new User.Builder()
                .hospitalIdNumber(request.hospitalIdNumber)
                .fullName(request.fullName)
                .passwordHash(passwordEncoder.encode(request.password))
                .enabled(false)
                .role(UserRole.ROLE_TECHNICIAN)
                .build();

        userRepository.save(user);
        return CrudResult.success();
    }

    /**
     * Parametre olarak alınan String nesnesinin numerik karakterler haricinde bir karakter içerip içermediğini kontrol eder.
     * @return içeriyor ise true, içermiyor ise false
     */
    private boolean isHospitalIdContainsAnyCharacterOtherThanNumericCharacters(String hospitalId) {
        for (int i = 0; i < hospitalId.length(); i++) {
            if (!Character.isDigit(hospitalId.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
