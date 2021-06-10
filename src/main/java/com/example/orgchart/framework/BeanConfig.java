package com.example.orgchart.framework;

import com.example.orgchart.app.storage.EmployeeStorageHandler;
import com.example.orgchart.app.usecases.employee.CreateEmployeeHierarchyUseCase;
import com.example.orgchart.app.usecases.employee.FindMySupervisorsUseCase;
import com.example.orgchart.storage.DefaultEmployeeStorageHandler;
import com.example.orgchart.storage.EmployeeRepo;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.example.orgchart*")
@EnableJpaRepositories("com.example.orgchart*")
public class BeanConfig {

    @Bean
    public CreateEmployeeHierarchyUseCase createEmployeeHierarchyUseCase(EmployeeStorageHandler employeeStorageHandler) {
        return new CreateEmployeeHierarchyUseCase(employeeStorageHandler);
    }

    @Bean
    public EmployeeStorageHandler employeeStorageHandler(EmployeeRepo employeeRepo) {
        return new DefaultEmployeeStorageHandler(employeeRepo);
    }

    @Bean
    public FindMySupervisorsUseCase findMySupervisorsUseCase(EmployeeStorageHandler employeeStorageHandler) {
        return new FindMySupervisorsUseCase(employeeStorageHandler);
    }
}
