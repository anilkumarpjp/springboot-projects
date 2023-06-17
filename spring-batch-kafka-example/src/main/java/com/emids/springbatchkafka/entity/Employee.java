package com.emids.springbatchkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    @Id
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String department;
    private String jobTitle;
    private Integer yearsOfExperience;
    private Integer salary;
}
