package com.example.orgchart.helper;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.storage.daos.EmployeeDAO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestHelper {

    public static final String JONAS = "Jonas";
    public static final String PETE = "Pete";
    public static final String NICK = "Nick";
    public static final String SOPHIE = "Sophie";
    public static final String BARBARA = "Barbara";

    public static Employee getEmployeesForTest() {
        Employee jonas = new Employee(JONAS);
        Employee pete = new Employee(PETE);
        Employee nick = new Employee(NICK);
        Employee sophie = new Employee(SOPHIE);
        Employee barbara = new Employee(BARBARA);

        jonas.addStaff(sophie);

        sophie.addStaff(nick);

        nick.addStaff(barbara);
        nick.addStaff(pete);

        return jonas;
    }

    public static List<EmployeeDAO> getEmployeeDAOsForTest() {
        List<EmployeeDAO> employeeDAOS = new ArrayList<>();
        employeeDAOS.add(new EmployeeDAO(JONAS, null));
        employeeDAOS.add(new EmployeeDAO(PETE, null));
        employeeDAOS.add(new EmployeeDAO(NICK, null));
        employeeDAOS.add(new EmployeeDAO(SOPHIE, null));
        employeeDAOS.add(new EmployeeDAO(BARBARA, null));
        return employeeDAOS;
    }

    public static Map<String, String> getSampleEmployeeHierarchyPayload() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put(PETE, NICK);
        result.put(BARBARA, NICK);
        result.put(NICK, SOPHIE);
        result.put(SOPHIE, JONAS);

        return result;
    }
}
