package com.example.orgchart.app.usecases;

import com.example.orgchart.app.exceptions.CeoException;
import com.example.orgchart.app.exceptions.StorageException;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.app.usecases.employee.CreateEmployeeHierarchyUseCase;
import com.example.orgchart.storage.DefaultEmployeeStorageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.example.orgchart.helper.TestHelper.*;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class CreateEmployeeHierarchyUseCaseTests {

    public static final String SOME_NEW_CEO = "SomeNewCeo";
    public static final String SOME_NEW_EMPLOYEE = "SomeNewEmployee";

    @Test
    void processSuccess() throws CeoException, StorageException {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = new CreateEmployeeHierarchyUseCase(employeeStorageHandler);

        Map<String, String> payload = getSampleEmployeeHierarchyPayload();

        //Act
        Map<String, Object> actual = createEmployeeHierarchyUseCase.process(payload);

        //Assert
        assertTrue(isNotEmpty(actual));
    }

    @Test
    void processDBStorageExceptionErrorFail() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = new CreateEmployeeHierarchyUseCase(employeeStorageHandler);

        doThrow(new StorageException("")).when(employeeStorageHandler).applyManagers(any());
        Map<String, String> payload = getSampleEmployeeHierarchyPayload();
        Assertions.assertThrows(StorageException.class, () -> {
                    //Act
                    createEmployeeHierarchyUseCase.process(payload);

                    //Assert
                    fail();
                }
        );
    }

    @Test
    void processDBErrorFail() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = new CreateEmployeeHierarchyUseCase(employeeStorageHandler);

        doThrow(new RuntimeException("")).when(employeeStorageHandler).applyManagers(any());
        Map<String, String> payload = getSampleEmployeeHierarchyPayload();
        Assertions.assertThrows(Exception.class, () -> {
                    //Act
                    createEmployeeHierarchyUseCase.process(payload);

                    //Assert
                    fail();
                }
        );
    }

    @Test
    void processMissingCEOFail() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = new CreateEmployeeHierarchyUseCase(employeeStorageHandler);

        Map<String, String> payload = getSampleEmployeeHierarchyPayload();
        payload.put(JONAS, SOPHIE);
        Assertions.assertThrows(CeoException.class, () -> {
                    //Act
                    createEmployeeHierarchyUseCase.process(payload);

                    //Assert
                    fail();
                }
        );
    }

    @Test
    void processTwoCEOsFail() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = new CreateEmployeeHierarchyUseCase(employeeStorageHandler);

        Map<String, String> payload = getSampleEmployeeHierarchyPayload();
        payload.put(SOME_NEW_EMPLOYEE, SOME_NEW_CEO);
        Assertions.assertThrows(CeoException.class, () -> {
                    //Act
                    createEmployeeHierarchyUseCase.process(payload);

                    //Assert
                    fail();
                }
        );
    }

}
