package com.example.rqchallenge.employees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EmployeeControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());
        employeeList.add(new Employee());

        when(employeeService.getAll()).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void getEmployeesByNameSearch() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());
        employeeList.add(new Employee());
        employeeList.add(new Employee());

        when(employeeService.findByName(anyString())).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/search/{searchString}", "john").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(101);
        employee.setEmployee_age(32);
        employee.setEmployee_name("John");
        employee.setEmployee_salary(1000);
        employee.setProfile_image("");

        when(employeeService.findById(anyString())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}}", "101").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.employee_age", is(32)))
                .andExpect(jsonPath("$.employee_name", is("John")))
                .andExpect(jsonPath("$.employee_salary", is(1000)))
                .andExpect(jsonPath("$.profile_image", isEmptyString()))
                .andDo(print());
    }

    @Test
    void getHighestSalaryOfEmployees() throws Exception {

        Integer highestSalary = 1000;

        when(employeeService.findHighestSalary()).thenReturn(highestSalary);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/highestSalary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(1000)))
                .andDo(print());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> highestEarningEmployees = Arrays.asList("John", "Jonathan", "Jimmy");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(highestEarningEmployees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/topTenHighestEarningEmployeeNames").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    void createEmployee() {
    }

    @Test
    void deleteEmployeeById() {
    }
}