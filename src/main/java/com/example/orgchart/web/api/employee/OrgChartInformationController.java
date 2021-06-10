package com.example.orgchart.web.api.employee;

import com.example.orgchart.app.exceptions.CeoException;
import com.example.orgchart.app.exceptions.StorageException;
import com.example.orgchart.app.usecases.employee.CreateEmployeeHierarchyUseCase;
import com.example.orgchart.app.usecases.employee.FindMySupervisorsUseCase;
import com.example.orgchart.storage.exceptions.EmployeeNotFoundException;
import com.example.orgchart.web.api.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class OrgChartInformationController extends BaseController {

    private final CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase;
    private final FindMySupervisorsUseCase findMySupervisorsUseCase;

    public OrgChartInformationController(CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase, FindMySupervisorsUseCase findMySupervisorsUseCase) {
        this.createEmployeeHierarchyUseCase = createEmployeeHierarchyUseCase;
        this.findMySupervisorsUseCase = findMySupervisorsUseCase;
    }

    @PostMapping("/employee/hierarchy")
    public ResponseEntity<Map<String, Object>> createEmployeeHierarchy(@RequestBody Map<String, String> employeePayload) throws CeoException, StorageException {
        log.info("Creating a org chat");
        return ResponseEntity.ok(createEmployeeHierarchyUseCase.process(employeePayload));
    }

    @GetMapping("/employee/{employeeName}/supervisors")
    public ResponseEntity<Map<String, Object>> getMySupervisors(@PathVariable("employeeName") String employeeName, @RequestParam(required = false, name = "depth", defaultValue = "1") int depth) throws EmployeeNotFoundException {
        log.info("Getting {} supervisors for {}", depth, employeeName);
        return ResponseEntity.ok(findMySupervisorsUseCase.process(employeeName, depth));
    }

}
