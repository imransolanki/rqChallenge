package com.example.rqchallenge;

import com.example.rqchallenge.model.api.response.GetAllEmployee.Employee;
import org.springframework.core.convert.converter.Converter;

public class EmployeeResponseToEmployeeConverter implements Converter<Employee, com.example.rqchallenge.employees.Employee> {
    @Override
    public com.example.rqchallenge.employees.Employee convert(Employee input) {
        return new com.example.rqchallenge.employees.Employee(input.getId(), input.getEmployeeName(), input.getEmployeeSalary(), input.getEmployeeAge(), input.getProfileImage());
    }
}
