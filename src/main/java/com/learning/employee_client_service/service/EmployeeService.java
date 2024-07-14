package com.learning.employee_client_service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learning.employee_client_service.dto.EmployeeDTO;

@Service
public class EmployeeService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${data-service-url}")
    private String baseUrl;

    public List<EmployeeDTO> getAllEmployees() {
        return Arrays.asList(restTemplate.getForObject(baseUrl, EmployeeDTO[].class));
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        return restTemplate.postForObject(baseUrl, employee, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee) {
        restTemplate.put(baseUrl + "/" + id, employee);
        return getEmployeeById(id);
    }

    public void deleteEmployeeById(Long id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}
