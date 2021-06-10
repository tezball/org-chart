package com.example.orgchart.storage;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.domain.OrgChart;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.storage.daos.EmployeeDAO;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultEmployeeStorageHandler implements EmployeeStorageHandler {

    private final EmployeeRepo employeeRepo;

    public DefaultEmployeeStorageHandler(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void saveEmployees(OrgChart orgChart) {
        List<Employee> employeeList = orgChart.getAllEmployees();
        Set<EmployeeDAO> employeeDAOS = convertToListOdEmployeeDTOS(employeeList);
        employeeRepo.saveAll(employeeDAOS);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void clearDb() {
        employeeRepo.deleteAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void applyManagers(OrgChart orgChart) {
        List<EmployeeDAO> applyManagers = assignManagers(employeeRepo.findAll(), orgChart.getAllEmployees());
        employeeRepo.saveAll(applyManagers);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Employee findEmployeeByName(String employeeName) throws EmployeeNotFoundException {
        EmployeeDAO employeeDAO = employeeRepo.findEmployeeDAOByName(employeeName);
        if (employeeDAO == null)
            throw new EmployeeNotFoundException("Unable to find employee " + employeeName + " in storage");
        return convertToEmployee(employeeDAO);
    }

    private List<EmployeeDAO> assignManagers(List<EmployeeDAO> employeeDAOS, List<Employee> allEmployees) {
        for (Employee employee : allEmployees) {
            String employeeName = employee.getName();
            Employee manager = employee.getManager();

            Optional<EmployeeDAO> targetEmployee = employeeDAOS.stream().filter(employeeDAO -> employeeName.equals(employeeDAO.getName())).findFirst();
            if (targetEmployee.isPresent()) {
                EmployeeDAO targetManager = employeeDAOS.stream().filter(currentManager -> {
                    if (null != manager)
                        return manager.getName().equals(currentManager.getName());
                    return false;
                }).findFirst().orElse(null);

                targetEmployee.get().setReportsTo(targetManager);
            }
        }
        return employeeDAOS;
    }

    private Employee convertToEmployee(EmployeeDAO employeeDAO) {
        Employee manager = null;
        if (employeeDAO.getReportsTo() != null)
            manager = new Employee(employeeDAO.getReportsTo().getName());
        return Employee
                .builder()
                .name(employeeDAO.getName())
                .manager(manager)
                .build();
    }

    private Set<EmployeeDAO> convertToListOdEmployeeDTOS(List<Employee> employeeList) {
        Set<EmployeeDAO> employeeDAOS = new HashSet<>();
        for (Employee employee : employeeList) {
            employeeDAOS.add(new EmployeeDAO(employee.getName(), null));
        }
        return employeeDAOS;
    }
}
