package az.trendyolaz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

record ErrorDetails(
        LocalDateTime timestamp,
        String message,
        String details
){}

@RestControllerAdvice

public class GlobalExceptionHandler  {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex  , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                errors.put(error.getField() , error.getDefaultMessage())
        );
        return  new  ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException (Exception ex , WebRequest request ){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Daxili sistem xətası: " + ex.getMessage(),
                request.getDescription(false)
        );
        return  new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
