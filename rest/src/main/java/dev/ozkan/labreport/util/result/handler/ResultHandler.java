package dev.ozkan.labreport.util.result.handler;

import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultHandler {
    public static ResponseEntity<?> handleFailureReason(OperationFailureReason reason, String message){
        switch (reason){
            case CONFLICT -> {
                return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.CONFLICT);
            }
            case PRECONDITION_FAILED -> {
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.PRECONDITION_FAILED);
            }
            case UNAUTHORIZED -> {
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.UNAUTHORIZED);
            }
            case NOT_FOUND -> {
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.NOT_FOUND);
            }
            default -> {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}

class ResponseMessage{
    public ResponseMessage(String message) {
        this.message = message;
    }

    String message;

    public String getMessage() {
        return message;
    }

    public ResponseMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
