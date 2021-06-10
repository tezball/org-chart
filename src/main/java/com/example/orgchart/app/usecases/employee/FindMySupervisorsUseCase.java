package com.example.orgchart.app.usecases.employee;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.domain.OrgChart;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FindMySupervisorsUseCase {

    private final EmployeeStorageHandler employeeStorageHandler;

    public FindMySupervisorsUseCase(EmployeeStorageHandler employeeStorageHandler) {
        this.employeeStorageHandler = employeeStorageHandler;
    }

    public Map<String, Object> process(String employeeName, int depth) throws EmployeeNotFoundException {

        Employee employee = employeeStorageHandler.findEmployeeByName(employeeName);

        Employee seniorSupervisor = findMostSeniorManager(employee, depth);

        return new OrgChart(seniorSupervisor).getEmployeeHierarchy();

    }

    private Employee findMostSeniorManager(Employee employee, int depth) {
        employee.setStaff(new ArrayList<>());
        Employee seniorSupervisor = employee;
        for (int i = 0; i < depth; i++) {
            if (seniorSupervisor.getManager() != null) {
                try {
                    Employee manager = employeeStorageHandler.findEmployeeByName(seniorSupervisor.getManager().getName());
                    manager.setStaff(Collections.singletonList(seniorSupervisor));
                    seniorSupervisor = manager;
                } catch (EmployeeNotFoundException e) {
                    break;
                }
            }
        }
        return seniorSupervisor;
    }
}
