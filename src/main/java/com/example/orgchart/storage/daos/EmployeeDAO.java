package com.example.orgchart.storage.daos;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE", schema = "ORGCHART")
public class EmployeeDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(unique = true)
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDAO reportsTo;

    public EmployeeDAO(String name, EmployeeDAO reportsTo) {
        this.name = name;
        this.reportsTo = reportsTo;
    }

    public EmployeeDAO() {
    }

    public String getName() {
        return this.name;
    }

    public EmployeeDAO getReportsTo() {
        return this.reportsTo;
    }

    public void setReportsTo(EmployeeDAO reportsTo) {
        this.reportsTo = reportsTo;
    }
}
