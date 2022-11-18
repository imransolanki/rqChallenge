package com.example.rqchallenge.employees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    Integer id;
    String name;
    Integer salary;
    Integer age;
    String profileImage;
}
