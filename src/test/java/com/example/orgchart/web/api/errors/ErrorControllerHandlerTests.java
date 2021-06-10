package com.example.orgchart.web.api.errors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ErrorControllerHandlerTests {

    @Test
    void catchAllExceptionHandlerSuccess() {
        //Arrange
        ErrorControllerHandler errorControllerHandler = new ErrorControllerHandler();

        //Act
        ResponseEntity<String> actual = errorControllerHandler.catchAllExceptionHandler(new RuntimeException());

        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    void invalidJsonForEndpointExceptionHandlerSuccess() {
        //Arrange
        ErrorControllerHandler errorControllerHandler = new ErrorControllerHandler();
        HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);

        //Act
        ResponseEntity<String> actual = errorControllerHandler.invalidJsonForEndpointExceptionHandler(new HttpMessageNotReadableException("message", httpInputMessage));

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void ceoExceptionHandlerSuccess() {
        //Arrange
        ErrorControllerHandler errorControllerHandler = new ErrorControllerHandler();

        //Act
        ResponseEntity<String> actual = errorControllerHandler.ceoExceptionHandler(new RuntimeException());

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void storageExceptionHandlerSuccess() {
        //Arrange
        ErrorControllerHandler errorControllerHandler = new ErrorControllerHandler();

        //Act
        ResponseEntity<String> actual = errorControllerHandler.storageExceptionHandler(new RuntimeException());

        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    void employeeNotFoundExceptionHandlerSuccess() {
        //Arrange
        ErrorControllerHandler errorControllerHandler = new ErrorControllerHandler();

        //Act
        ResponseEntity<String> actual = errorControllerHandler.employeeNotFoundExceptionHandler(new RuntimeException());

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

}
