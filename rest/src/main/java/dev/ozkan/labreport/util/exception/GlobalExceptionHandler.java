package dev.ozkan.labreport.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("""
                        {"message" : "Invalid date time format"}
                        """);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorMessage message = new ErrorMessage();
        var exception = Objects.requireNonNull(ex.getDetailMessageArguments())[1].toString();
        message.addValidationErrorMessagesToList(exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message",message.getMessage()));
    }


    class ErrorMessage{
        String message;


        void addValidationErrorMessagesToList(String message){
            if (message.contains("hospital")) {
                this.message =("Hospital id can't be empty. Please provide a valid hospital ID.");
                return;
            }

            if (message.contains("password")) {
                this.message ="Password must be at least " + 6 + " characters long. Please choose a longer password.";
                return;
            }

            if (message.contains("surname")) {
                this.message="Surname can't be empty. Please provide a surname.";
                return;
            }

            if (message.contains("name")) {
                this.message = "Name can't be empty. Please provide a name.";
            }


        }

        String getMessage(){
            return message;
        }
    }

}
