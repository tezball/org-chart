package com.example.orgchart.app.usecases.employee;

import com.example.orgchart.app.domain.Employee;
import com.example.orgchart.app.domain.OrgChart;
import com.example.orgchart.app.exceptions.CeoException;
import com.example.orgchart.app.exceptions.StorageException;
import com.example.orgchart.app.storage.EmployeeStorageHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class CreateEmployeeHierarchyUseCase {

    private final EmployeeStorageHandler employeeStorageHandler;

    public CreateEmployeeHierarchyUseCase(EmployeeStorageHandler employeeStorageHandler) {
        this.employeeStorageHandler = employeeStorageHandler;
    }

    public Map<String, Object> process(Map<String, String> employeePayload) throws CeoException, StorageException {

        Set<String> possibleCeos = new HashSet<>(employeePayload.values());
        Set<Employee> allEmployees = new HashSet<>();

        assignStaffToManagers(employeePayload, possibleCeos, allEmployees);
        Employee ceo = findCeo(possibleCeos, allEmployees);

        OrgChart orgChart = new OrgChart(ceo);

        if (!saveChart(orgChart))
            throw new StorageException("Chart has not been saved, please try again later. If the issue continues please contact support");

        return orgChart.getEmployeeHierarchy();

    }

    private void assignStaffToManagers(Map<String, String> employeePayload, Set<String> possibleCeos, Set<Employee> allEmployees) {
        for (Map.Entry<String, String> entry : employeePayload.entrySet()) {
            var staff = getEmployeeFromListIfPresent(allEmployees, entry.getKey());
            var manager = getEmployeeFromListIfPresent(allEmployees, entry.getValue());

            manager.addStaff(staff);
            possibleCeos.remove(staff.getName());

            allEmployees.add(staff);
            allEmployees.add(manager);
        }
    }

    private boolean saveChart(OrgChart orgChart) {
        try {
            employeeStorageHandler.clearDb();//...... :(
            employeeStorageHandler.saveEmployees(orgChart);
            employeeStorageHandler.applyManagers(orgChart);
            return true;
        } catch (Exception e) {
            log.error("{} - Can't save employees to DB", this.getClass().getName(), e);
            return false;
        }
    }

    private Employee findCeo(Set<String> managers, Set<Employee> allEmployees) throws CeoException {
        if (managers.size() == 1) {
            Optional<String> ceoName = managers.stream().findFirst();
            Optional<Employee> ceo = allEmployees.stream().filter(employee -> employee.getName().equals(ceoName.get())).findFirst();
            if (ceo.isPresent())
                return ceo.get();
        }
        throw new CeoException("The CEO can't be found. Please add a single CEO");
    }

    private Employee getEmployeeFromListIfPresent(Set<Employee> allEmployees, String name) {
        var staff = new Employee(name);
        if (allEmployees.contains(staff)) {
            for (Employee employee : allEmployees) {
                if (employee.equals(staff)) {
                    staff = employee;
                    break;
                }
            }
        }
        return staff;
    }
}
