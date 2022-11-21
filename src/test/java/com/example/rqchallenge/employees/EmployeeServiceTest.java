package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.api.response.GetAllEmployee.Employee;
import com.example.rqchallenge.model.api.response.GetAllEmployee.GetAllEmployeeResponse;
import com.example.rqchallenge.model.api.response.findById.Data;
import com.example.rqchallenge.model.api.response.findById.FindByIdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAll() throws IOException {
        GetAllEmployeeResponse mockedResponse = new GetAllEmployeeResponse();
        mockedResponse.setStatus("success");
        mockedResponse.setData(Arrays.asList(new Employee(), new Employee()));

        Mockito
                .when(restTemplate.getForEntity("https://dummy.restapiexample.com" + "/api/v1/employees", GetAllEmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockedResponse, HttpStatus.OK));

        List<com.example.rqchallenge.employees.Employee> response = employeeService.getAll();

        Assertions.assertEquals(2, response.size());
    }

    @Test
    void findById() {
        FindByIdResponse mockedResponse = new FindByIdResponse();
        mockedResponse.setStatus("success");
        Data data = new Data();
        data.setId(101);
        mockedResponse.setData(data);

        Mockito
                .when(restTemplate.getForEntity("https://dummy.restapiexample.com" + "/api/v1/employee/101", FindByIdResponse.class))
                .thenReturn(new ResponseEntity<>(mockedResponse, HttpStatus.OK));

        com.example.rqchallenge.employees.Employee response = employeeService.findById("101");
        Assertions.assertEquals(101, response.getId());
    }

    @Test
    void findHighestSalary() {
        GetAllEmployeeResponse mockedResponse = new GetAllEmployeeResponse();
        mockedResponse.setStatus("success");

        Employee first = new Employee();
        first.setEmployeeSalary(1000);

        Employee second = new Employee();
        second.setEmployeeSalary(2000);

        mockedResponse.setData(Arrays.asList(first, second));

        Mockito
                .when(restTemplate.getForEntity("https://dummy.restapiexample.com" + "/api/v1/employees", GetAllEmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockedResponse, HttpStatus.OK));

        Integer highestSalary = employeeService.findHighestSalary();
        Assertions.assertEquals(2000, highestSalary);
    }

    @Test
    void deleteById() {
        Mockito
                .doNothing()
                .when(restTemplate).delete("https://dummy.restapiexample.com" + "/api/v1/delete/101");

        String response = employeeService.deleteById("101");
        Assertions.assertEquals("success", response);
    }
}
