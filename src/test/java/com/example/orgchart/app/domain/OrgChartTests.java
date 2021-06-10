package com.example.orgchart.app.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.example.orgchart.helper.TestHelper.JONAS;
import static com.example.orgchart.helper.TestHelper.getEmployeesForTest;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrgChartTests {

    @Test
    void getEmployeeHierarchySuccess() {
        //Arrange
        Employee ceo = getEmployeesForTest();
        OrgChart chart = new OrgChart(ceo);

        //Act
        Map<String, Object> actual = chart.getEmployeeHierarchy();

        //Assert
        assertTrue(isNotEmpty(actual));
        assertTrue(actual.containsKey(JONAS));
    }

    @Test
    void getAllEmployeesSuccess() {
        //Arrange
        Employee ceo = getEmployeesForTest();
        OrgChart chart = new OrgChart(ceo);

        //Act
        List<Employee> actual = chart.getAllEmployees();

        //Assert
        assertEquals(5, actual.size());
    }
}
