package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.api.response.Create.CreateEmployeeResponse;
import com.example.rqchallenge.model.api.response.GetAllEmployee.GetAllEmployeeResponse;
import com.example.rqchallenge.model.api.response.findById.Data;
import com.example.rqchallenge.model.api.response.findById.FindByIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    private static final String BASE_URL = "https://dummy.restapiexample.com";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ConversionService conversionService;

    public List<Employee> getAll() throws IOException {
        ArrayList<Employee> result = new ArrayList<>();

        try {
            ResponseEntity<GetAllEmployeeResponse> response = restTemplate.getForEntity(BASE_URL + "/api/v1/employees", GetAllEmployeeResponse.class);

            if (!"success".equalsIgnoreCase(response.getBody().getStatus())) {
                throw new Exception("failure");
            }
            List<com.example.rqchallenge.model.api.response.GetAllEmployee.Employee> employeeList = response.getBody().getData();
            log.info("Number of employees {}", employeeList.size());
            employeeList.forEach(employee -> result.add(conversionService.convert(employee, Employee.class)));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException("failure");
        }
        return result;
    }

    public List<Employee> findByName(String searchString) {
        try {
            List<Employee> employeeList = getAll();
            return employeeList
                    .stream()
                    .filter(employee -> employee.name.contains(searchString))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Employee findById(String id) {
        try {
            ResponseEntity<FindByIdResponse> response = restTemplate.getForEntity(BASE_URL + "/api/v1/employee/" + id, FindByIdResponse.class);

            if (!"success".equalsIgnoreCase(response.getBody().getStatus())) {
                throw new Exception("failure");
            }
            Data data = response.getBody().getData();
            log.info("Employee found with the id {}", data.getId());

            return new Employee(data.getId(), data.getEmployeeName(), data.getEmployeeSalary(), data.getEmployeeAge(), data.getProfileImage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new Employee();
    }

    public Integer findHighestSalary() {
        try {
            List<Employee> employeeList = getAll();
            Integer highestSalary = employeeList
                    .stream()
                    .max((first, second) -> second.salary - first.salary)
                    .get()
                    .getSalary();
            return highestSalary;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        try {
            List<Employee> employeeList = getAll();
            List<String> result = employeeList.stream()
                    .sorted((first, second) -> second.salary - first.salary)
                    .limit(10)
                    .map(employee -> employee.name)
                    .collect(Collectors.toList());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee create(Map<String, Object> employeeInput) throws Exception {
        ResponseEntity<CreateEmployeeResponse> response =
                restTemplate.postForEntity(BASE_URL + "/api/v1/create", null, CreateEmployeeResponse.class, employeeInput);
        if (!"success".equalsIgnoreCase(response.getBody().getStatus())) {
            throw new Exception("failure");
        }
        com.example.rqchallenge.model.api.response.Create.Data data = response.getBody().getData();
        log.info(data.toString());
        return new Employee(data.getId(), data.getName(), Integer.parseInt(data.getSalary()), data.getAge(), "");
    }

    public String deleteById(String id) {
        try {
            restTemplate.delete(BASE_URL + "/api/v1/delete/" + id);
            log.info("Employee deleted with the id {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "success";
    }

}
