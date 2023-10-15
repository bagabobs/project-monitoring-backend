package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;

import java.util.List;

public interface LoadEmployeeUseCase {
    List<Employee> loadAllEmployee() throws UserManagementException;

    List<Employee> loadEmployeeByPage(Long id, int size) throws UserManagementException;
}
