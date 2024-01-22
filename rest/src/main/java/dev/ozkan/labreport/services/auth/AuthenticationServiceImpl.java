package dev.ozkan.labreport.services.auth;

import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.services.jwt.JwtService;
import dev.ozkan.labreport.util.result.AuthenticationResult;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Gelen istek içerisinde bulunan Hastahane kimlik numarasının ve parolanın doğruluğunu kontrol eder.
     * Kullanıcının aktif olup olmadığını kontrol eder.
     * @return Eğer bilgiler doğru ise ve kullanıcı aktif ise olumlu değil ise olumsuz sonuç döner.
     */
    @Override
    public AuthenticationResult authenticate(AuthenticationServiceRequest request) {
        final var userOptional = userRepository.getUserByHospitalIdNumber(request.hospitalIdNumber);
        if (userOptional.isEmpty()){
            return AuthenticationResult.failed(OperationFailureReason.NOT_FOUND,"Invalid Credentials");
        }

        final var userFromDb = userOptional.get();
        if (!passwordEncoder.matches(request.password, userFromDb.getPassword())){
            return AuthenticationResult.failed(OperationFailureReason.NOT_FOUND,"Invalid Credentials");
        }

        if (!userFromDb.isEnabled()){
            return AuthenticationResult.failed(OperationFailureReason.UNAUTHORIZED,"Your account is not yet enabled. Please contact your manager for assistance.");
        }

        return AuthenticationResult.success(jwtService.generateToken(userFromDb.getHospitalIdNumber()));
    }
}
