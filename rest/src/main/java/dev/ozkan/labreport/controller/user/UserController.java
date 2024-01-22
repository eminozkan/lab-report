package dev.ozkan.labreport.controller.user;

import dev.ozkan.labreport.services.user.UserService;
import dev.ozkan.labreport.util.result.handler.ResultHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PatchMapping("/{userId}")
    ResponseEntity<?> switchUserIsEnabled(@PathVariable String userId){
        var result = userService.switchUserIsEnabled(userId);
        if (result.isSuccess()){
            return ResponseEntity.ok("User enable status has been changed.");
        }
        return ResultHandler.handleFailureReason(result.getReason(),result.getMessage());
    }
}
