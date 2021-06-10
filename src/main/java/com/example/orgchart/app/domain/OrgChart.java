package com.example.orgchart.app.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrgChart {

    Employee root;

    public OrgChart(Employee employee) {
        this.root = employee;
    }

    public Map<String, Object> getEmployeeHierarchy() {
        Map<String, Object> temp = new LinkedHashMap<>();
        temp = getEmployeeHierarchy(root, temp);
        LinkedHashMap<String, Object> org = new LinkedHashMap<>();

        org.put(root.getName(), temp);
        return org;
    }

    public Map<String, Object> getEmployeeHierarchy(Employee me, Map<String, Object> bossStaff) {
        LinkedHashMap<String, Object> emptyList = new LinkedHashMap<>();
        bossStaff.put(me.name, emptyList);

        for (Employee employee : me.staff) {
            emptyList.put(employee.getName(), getEmployeeHierarchy(employee, emptyList));
        }
        return emptyList;
    }

    private List<Employee> getAllEmployeesFromStaff(Employee root, List<Employee> employeeList) {
        employeeList.add(root);
        for (Employee employee : root.staff)
            getAllEmployeesFromStaff(employee, employeeList);
        return employeeList;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        return getAllEmployeesFromStaff(root, employeeList);
    }
}
