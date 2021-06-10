package com.example.orgchart.web.api.errors;

import com.example.orgchart.app.exceptions.CeoException;
import com.example.orgchart.app.exceptions.StorageException;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorControllerHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchAllExceptionHandler(Throwable e) {
        log.error("Catch all exception for web", e);
        return new ResponseEntity<>("An issue has occurred, please contact support for help", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> invalidJsonForEndpointExceptionHandler(Throwable e) {
        log.error("Invalid Json for endpoint", e);
        return new ResponseEntity<>("The payload can't be read, please provide a valid json payload for this endpoint", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CeoException.class)
    public ResponseEntity<String> ceoExceptionHandler(Throwable e) {
        log.error("can't find ceo exception", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> storageExceptionHandler(Throwable e) {
        log.error("storage exception", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> employeeNotFoundExceptionHandler(Throwable e) {
        log.error("Employee not found", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
