package com.example.orgchart.app.storage;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.domain.OrgChart;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;

public interface EmployeeStorageHandler {

    void saveEmployees(OrgChart orgChart);

    void clearDb();

    void applyManagers(OrgChart orgChart);

    Employee findEmployeeByName(String employeeName) throws EmployeeNotFoundException;
}
