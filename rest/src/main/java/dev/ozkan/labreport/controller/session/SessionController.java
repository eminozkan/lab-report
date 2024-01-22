package dev.ozkan.labreport.controller.session;

import dev.ozkan.labreport.model.session.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping()
    ResponseEntity<?> getSession(UserSession session){
        SessionPayload sessionPayload = new SessionPayload().setUserRole(session.getUserRole().toString());
        return ResponseEntity.ok(sessionPayload);
    }
}

class SessionPayload{
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public SessionPayload setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }
}
