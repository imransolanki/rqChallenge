package com.example.rqchallenge.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(101);
        employee.setAge(32);
        employee.setName("John");
        employee.setSalary(1000);
        employee.setProfileImage("");

        when(employeeService.findById(anyString())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}}", "101").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.age", is(32)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.salary", is(1000)))
                .andExpect(jsonPath("$.profileImage", isEmptyString()))
                .andDo(print());
    }

    @Test
    void getHighestSalaryOfEmployees() throws Exception {

        Integer highestSalary = 1000;

        when(employeeService.findHighestSalary()).thenReturn(highestSalary);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/highestSalary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1000)))
                .andDo(print());
    }

    /*@Test
    void getTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> highestEarningEmployees = Arrays.asList("John", "Jonathan", "Jimmy");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(highestEarningEmployees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/topTenHighestEarningEmployeeNames").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }*/

    /*@Test
    void createEmployee() throws Exception {
        Map<String, Object> employeeInput = new HashMap<>() {{
            put("name", "John");
            put("salary", "1000");
            put("age", "32");
        }};

        when(employeeService.create(any(Employee.class))).thenReturn(new Employee());

        mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employeeInput)))
                .andExpect(status().isCreated())
                .andDo(print());
    }*/

    @Test
    void deleteEmployeeById() throws Exception {
        String deletedEmployee = "John";

        when(employeeService.deleteById(any(String.class))).thenReturn(deletedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/{id}", "101").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("John")))
                .andDo(print());
    }
}
