package com.example.orgchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.orgchart.*")
public class OrgChartApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgChartApplication.class, args);
    }

}
