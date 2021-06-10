package com.example.orgchart.app.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeTests {

    public static final String SOME_STAFF_NAME = "someStaffName";
    public static final String SOME_MANAGER_NAME = "someManagerName";
    private static final String SOME_EMPLOYEE_NAME = "someEmployeeName";

    @Test
    void addStaffSuccess() {
        //Arrange
        Employee staff = new Employee(SOME_STAFF_NAME);
        Employee manager = new Employee(SOME_MANAGER_NAME);

        //Act
        manager.addStaff(staff);

        //Assert
        assertTrue(isNotEmpty(manager.staff));
        assertEquals(staff, manager.staff.get(0));
    }

    @Test
    void EmployeeConstructorSuccess() {
        //Arrange
        Employee staff = new Employee(SOME_STAFF_NAME);
        Employee manager = new Employee(SOME_MANAGER_NAME);
        List<Employee> staffList = new ArrayList<>();
        staffList.add(staff);

        //Act
        Employee actual = new Employee(SOME_EMPLOYEE_NAME, staffList, manager);

        //Assert
        assertTrue(isNotEmpty(actual.staff));
        assertEquals(staff, actual.staff.get(0));
        assertEquals(SOME_EMPLOYEE_NAME, actual.getName());
        assertEquals(manager, actual.getManager());
    }
}
