package com.example.orgchart.storage;

import com.example.orgchart.storage.daos.EmployeeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<EmployeeDAO, Long> {
    EmployeeDAO findEmployeeDAOByName(String employeeName);
}
