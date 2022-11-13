package com.example.rqchallenge.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
public class EmployeeControllerImpl implements IEmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return new ResponseEntity<>(employeeService.findByName(searchString), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return new ResponseEntity<>(employeeService.findHighestSalary(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return new ResponseEntity<>(employeeService.getTopTenHighestEarningEmployeeNames(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        Employee employee = new Employee();
        employee.setEmployee_name(employeeInput.get("name").toString());
        employee.setEmployee_age(Integer.parseInt(employeeInput.get("age").toString()));
        employee.setEmployee_salary(Integer.parseInt(employeeInput.get("salary").toString()));

        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.deleteById(id), HttpStatus.OK);
    }
}
