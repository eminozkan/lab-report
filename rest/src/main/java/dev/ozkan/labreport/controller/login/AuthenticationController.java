package dev.ozkan.labreport.controller.login;

import dev.ozkan.labreport.config.security.CookieProperties;
import dev.ozkan.labreport.services.auth.AuthenticationService;
import dev.ozkan.labreport.util.result.handler.ResultHandler;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth/login")
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;
    private final CookieProperties properties;

    public AuthenticationController(AuthenticationService authService, CookieProperties properties) {
        this.authService = authService;
        this.properties = properties;
    }

    @PostMapping
    ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse response) {
        var result = authService.authenticate(request.toServiceRequest());
        if (result.isSuccess()) {

            ResponseCookie cookie = ResponseCookie.from(properties.getCookieName(), result.getAccessToken())
                    .httpOnly(true)
                    .secure(false)
                    .path(properties.getCookiePath())
                    .maxAge(properties.getCookieExpiration())
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity
                    .status(200)
                    .body(Map.of("message", "Login successful!"));
        }
        return ResultHandler.handleFailureReason(result.getReason(),result.getMessage());
    }

}
