package app.controler;

import app.exception.DataNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class AppExceptionHandler {

    @ExceptionHandler({DataNotFound.class})
    public final ResponseEntity handleNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Данные не найдены");
    }

}
