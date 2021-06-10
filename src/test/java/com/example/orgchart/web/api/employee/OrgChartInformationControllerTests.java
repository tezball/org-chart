package com.example.orgchart.web.api.employee;

import com.example.orgchart.app.usecases.employee.CreateEmployeeHierarchyUseCase;
import com.example.orgchart.app.usecases.employee.FindMySupervisorsUseCase;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.example.orgchart.helper.TestHelper.NICK;
import static com.example.orgchart.helper.TestHelper.getSampleEmployeeHierarchyPayload;
import static org.mockito.Mockito.*;

class OrgChartInformationControllerTests {

    @Test
    void createEmployeeHierarchySuccess() {
        //Arrange
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = mock(CreateEmployeeHierarchyUseCase.class);
        FindMySupervisorsUseCase findMySupervisorsUseCase = mock(FindMySupervisorsUseCase.class);

        OrgChartInformationController orgChartInformationController = new OrgChartInformationController(createEmployeeHierarchyUseCase, findMySupervisorsUseCase);
        Map<String, String> payload = getSampleEmployeeHierarchyPayload();

        //Act
        orgChartInformationController.createEmployeeHierarchy(payload);

        //Assert
        verify(createEmployeeHierarchyUseCase, times(1)).process(any());
        verify(findMySupervisorsUseCase, never()).process(anyString(), anyInt());
    }

    @Test
    void getMySupervisorsSuccess() {
        //Arrange
        CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase = mock(CreateEmployeeHierarchyUseCase.class);
        FindMySupervisorsUseCase findMySupervisorsUseCase = mock(FindMySupervisorsUseCase.class);

        OrgChartInformationController orgChartInformationController = new OrgChartInformationController(createEmployeeHierarchyUseCase, findMySupervisorsUseCase);

        //Act
        orgChartInformationController.getMySupervisors(NICK, 2);

        //Assert
        verify(createEmployeeHierarchyUseCase, never()).process(any());
        verify(findMySupervisorsUseCase, times(1)).process(anyString(), anyInt());

    }
}
