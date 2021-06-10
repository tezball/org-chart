package com.example.orgchart.app.usecases;


import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.app.usecases.employee.FindMySupervisorsUseCase;
import com.example.orgchart.storage.DefaultEmployeeStorageHandler;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.example.orgchart.helper.TestHelper.*;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindMySupervisorsUseCaseTests {

    @Test
    void processSuccess() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        FindMySupervisorsUseCase findMySupervisorsUseCase = new FindMySupervisorsUseCase(employeeStorageHandler);

        Employee nick = new Employee(NICK);
        Employee sophie = new Employee(SOPHIE);
        Employee jonas = new Employee(JONAS);

        jonas.addStaff(sophie);
        sophie.addStaff(nick);

        when(employeeStorageHandler.findEmployeeByName(nick.getName())).thenReturn(nick);
        when(employeeStorageHandler.findEmployeeByName(sophie.getName())).thenReturn(sophie);
        when(employeeStorageHandler.findEmployeeByName(jonas.getName())).thenReturn(jonas);

        int depth = 2;

        //Act
        Map<String, Object> actual = findMySupervisorsUseCase.process(NICK, depth);


        //Assert
        assertTrue(isNotEmpty(actual));
    }

    @Test
    void processUserNotFoundBrakeEarlySuccess() {
        //Arrange
        EmployeeStorageHandler employeeStorageHandler = mock(DefaultEmployeeStorageHandler.class);
        FindMySupervisorsUseCase findMySupervisorsUseCase = new FindMySupervisorsUseCase(employeeStorageHandler);

        Employee nick = new Employee(NICK);
        Employee sophie = new Employee(SOPHIE);
        Employee jonas = new Employee(JONAS);

        jonas.addStaff(sophie);
        sophie.addStaff(nick);

        when(employeeStorageHandler.findEmployeeByName(nick.getName())).thenReturn(nick);
        when(employeeStorageHandler.findEmployeeByName(sophie.getName())).thenReturn(sophie);
        when(employeeStorageHandler.findEmployeeByName(jonas.getName())).thenThrow(new EmployeeNotFoundException(""));

        int depth = 2;

        //Act
        Map<String, Object> actual = findMySupervisorsUseCase.process(NICK, depth);


        //Assert
        assertTrue(isNotEmpty(actual));
    }


}
