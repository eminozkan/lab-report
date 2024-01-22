package dev.ozkan.labreport.controller.register;

import dev.ozkan.labreport.services.registration.RegistrationService;
import dev.ozkan.labreport.util.result.handler.ResultHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/register")
@Validated
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request){
        var result = registrationService.register(request.toServiceRequest());
        if (result.isSuccess()){
            return ResponseEntity
                    .status(201)
                    .body(Map.of("message","Registered successfully"));
        }
        return ResultHandler.handleFailureReason(result.getReason(),result.getMessage());
    }
}
