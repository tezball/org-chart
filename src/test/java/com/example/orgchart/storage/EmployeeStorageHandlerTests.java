package com.example.orgchart.storage;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.domain.OrgChart;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.helper.TestHelper;
import com.example.orgchart.storage.daos.EmployeeDAO;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class EmployeeStorageHandlerTests {

    @Test
    void saveEmployeeSuccess() {
        //Arrange
        EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
        EmployeeStorageHandler employeeStorageHandler = new DefaultEmployeeStorageHandler(employeeRepo);

        OrgChart orgChart = new OrgChart(TestHelper.getEmployeesForTest());

        //Act
        employeeStorageHandler.saveEmployees(orgChart);

        //Assert
        verify(employeeRepo, times(1)).saveAll(any());
    }

    @Test
    void clearDbSuccess() {
        //Arrange
        EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
        EmployeeStorageHandler employeeStorageHandler = new DefaultEmployeeStorageHandler(employeeRepo);

        //Act
        employeeStorageHandler.clearDb();

        //Assert
        verify(employeeRepo, times(1)).deleteAll();
    }

    @Test
    void applyManagersSuccess() {
        //Arrange
        EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
        EmployeeStorageHandler employeeStorageHandler = new DefaultEmployeeStorageHandler(employeeRepo);

        List<EmployeeDAO> employeeDAOList = TestHelper.getEmployeeDAOsForTest();

        when(employeeRepo.findAll()).thenReturn(employeeDAOList);

        OrgChart orgChart = new OrgChart(TestHelper.getEmployeesForTest());

        //Act
        employeeStorageHandler.applyManagers(orgChart);

        //Assert
        verify(employeeRepo, times(1)).saveAll(any());
    }

    @Test
    void findEmployeeByNameSuccess() {
        //Arrange
        EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
        EmployeeStorageHandler employeeStorageHandler = new DefaultEmployeeStorageHandler(employeeRepo);
        EmployeeDAO sophie = new EmployeeDAO(TestHelper.SOPHIE, null);
        EmployeeDAO nick = new EmployeeDAO(TestHelper.NICK, sophie);

        when(employeeRepo.findEmployeeDAOByName(TestHelper.NICK)).thenReturn(nick);

        //Act
        Employee actual = employeeStorageHandler.findEmployeeByName(TestHelper.NICK);

        //Assert
        verify(employeeRepo, times(1)).findEmployeeDAOByName(any());
        assertNotNull(actual);
        Assertions.assertEquals(TestHelper.NICK, actual.getName());
    }

    @Test
    void findEmployeeByNameNullFail() {
        //Arrange
        EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
        EmployeeStorageHandler employeeStorageHandler = new DefaultEmployeeStorageHandler(employeeRepo);

        when(employeeRepo.findEmployeeDAOByName(any())).thenReturn(null);

        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            //Act
            employeeStorageHandler.findEmployeeByName(TestHelper.NICK);

            //Assert
            fail();
        });
    }
}
