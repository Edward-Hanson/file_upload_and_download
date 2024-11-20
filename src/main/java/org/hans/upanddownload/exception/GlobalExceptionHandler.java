package org.hans.upanddownload.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MediaException.class)
    public ResponseEntity<ExceptionDto> handleMediaException(MediaException e) {
        log.error("Media Exception: ", e);
        ExceptionDto errorDetails = ExceptionDto.builder()
                .exceptionCode(HttpStatus.BAD_REQUEST.value())
                .message(List.of(e.getMessage()))
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("Validation Exception: {}", errors);

        List<String> errorMessages = errors.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .toList();

        ExceptionDto errorDetails = ExceptionDto.builder()
                .timeStamp(LocalDateTime.now())
                .exceptionCode(HttpStatus.BAD_REQUEST.value())
                .message(errorMessages)
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleAllUncaughtException(Exception e) {
        log.error("Unexpected exception occurred: ", e);
        ExceptionDto errorDetails = ExceptionDto.builder()
                .exceptionCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(List.of(e.getMessage()))
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
