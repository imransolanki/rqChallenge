package com.example.rqchallenge.employees;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    public List<Employee> getAll() {
        return new ArrayList<>();
    }

    public List<Employee> findByName(String searchString) {
        return new ArrayList<>();
    }

    public Employee findById(String id) {
        return new Employee();
    }

    public Integer findHighestSalary() {
        return null;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    public Employee create(Employee employee) {
        return new Employee();
    }

    public String deleteById(String id) {
        return null;
    }
}
