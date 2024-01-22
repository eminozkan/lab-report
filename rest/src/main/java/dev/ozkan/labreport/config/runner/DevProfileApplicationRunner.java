package dev.ozkan.labreport.config.runner;

import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.model.user.UserRole;
import dev.ozkan.labreport.repository.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DevProfileApplicationRunner implements CommandLineRunner {

    private final Environment environment;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DevProfileApplicationRunner(Environment environment, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.environment = environment;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        String activeProfile = environment.getProperty("spring.profiles.active");

        if (activeProfile!= null && activeProfile.equals("dev")){
            if (userRepository.getUserByHospitalIdNumber("0123456").isEmpty()){
                User defaultUser = new User.Builder()
                        .fullName("Admin")
                        .hospitalIdNumber("0123456")
                        .passwordHash(passwordEncoder.encode("admin"))
                        .enabled(true)
                        .role(UserRole.ROLE_MANAGER)
                        .build();

                userRepository.save(defaultUser);
            }
        }


    }
}
