package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/v1/employee/getbyid/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        try {
            Optional<Employee> employeeOptional = loadEmployeeUseCase.loadEmployeeById((long) id);
            if (employeeOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            Employee employee = employeeOptional.get();
            return ResponseEntity.ok().body(employee);
        } catch(UserManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
