package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoadEmployeeController {
    private final LoadEmployeeUseCase loadEmployeeUseCase;

    public LoadEmployeeController(LoadEmployeeUseCase loadEmployeeUseCase) {
        this.loadEmployeeUseCase = loadEmployeeUseCase;
    }

    @GetMapping("/v1/employee/getall")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        try {
            List<Employee> employees = loadEmployeeUseCase.loadAllEmployee();
            return ResponseEntity.ok().body(employees);
        } catch(UserManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/v1/employee/getbypage")
    public ResponseEntity<List<Employee>> getEmployeeByPage(@RequestParam int page, @RequestParam int size) {
        try {
            List<Employee> employees = loadEmployeeUseCase.loadEmployeeByPage((long) page, size);
            return ResponseEntity.ok().body(employees);
        } catch(UserManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
