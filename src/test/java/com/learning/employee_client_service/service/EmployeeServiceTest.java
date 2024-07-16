package com.learning.employee_client_service.service;

import com.learning.employee_client_service.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

//import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService employeeService;

    @Value("${data.service.url}")
    private String baseUrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService();
        employeeService.restTemplate = restTemplate;
        employeeService.baseUrl = baseUrl;
    }

    @Test
    public void testGetAllEmployees() {
        EmployeeDTO[] employees = new EmployeeDTO[] { new EmployeeDTO(1L, "John Doe", "Developer") };
        when(restTemplate.getForObject(baseUrl, EmployeeDTO[].class)).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetEmployeeById() {
        EmployeeDTO employee = new EmployeeDTO(1L, "John Doe", "Developer");
        when(restTemplate.getForObject(baseUrl + "/" + 1, EmployeeDTO.class)).thenReturn(employee);

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employee = new EmployeeDTO(1L, "John Doe", "Developer");
        when(restTemplate.postForObject(baseUrl, employee, EmployeeDTO.class)).thenReturn(employee);

        EmployeeDTO result = employeeService.createEmployee(employee);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeDTO employee = new EmployeeDTO(1L, "John Doe", "Developer");
        doNothing().when(restTemplate).put(baseUrl + "/" + 1, employee);
        when(restTemplate.getForObject(baseUrl + "/" + 1, EmployeeDTO.class)).thenReturn(employee);

        EmployeeDTO result = employeeService.updateEmployee(1L, employee);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(restTemplate).delete(baseUrl + "/" + 1);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(restTemplate, times(1)).delete(baseUrl + "/" + 1);
    }
}
