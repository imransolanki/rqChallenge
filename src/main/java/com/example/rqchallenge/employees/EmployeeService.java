package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.api.response.GetAllEmployee.GetAllEmployeeResponse;
import com.example.rqchallenge.model.api.response.findById.Data;
import com.example.rqchallenge.model.api.response.findById.FindByIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

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
            log.info("Number of employees {}", response.getBody().getData().size());
            response.getBody().getData().forEach(employee -> result.add(conversionService.convert(employee, Employee.class)));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException("failure");
        }
        return result;
    }

    public List<Employee> findByName(String searchString) {
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

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

}
