package com.example.orgchart.app.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Builder
@EqualsAndHashCode
public class Employee {

    String name;

    @EqualsAndHashCode.Exclude
    List<Employee> staff;

    @EqualsAndHashCode.Exclude
    Employee manager;

    public Employee(String name) {
        this.name = name;
        staff = new ArrayList<>();
    }

    public Employee(String name, List<Employee> staff, Employee manager) {
        this.name = name;
        this.staff = staff;
        this.manager = manager;
    }

    public void addStaff(Employee employee) {
        staff.add(employee);
        employee.reportsTo(this);
    }

    private void reportsTo(Employee employee) {
        this.manager = employee;
    }

    public String getName() {
        return this.name;
    }

    public Employee getManager() {
        return this.manager;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }

}
