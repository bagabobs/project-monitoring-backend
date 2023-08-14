package com.baga.promon.usermanagement.application.service;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadEmployeeService implements LoadEmployeeUseCase {
    private final LoadEmployeePort loadEmployeePort;

    public LoadEmployeeService(LoadEmployeePort loadEmployeePort) {
        this.loadEmployeePort = loadEmployeePort;
    }

    @Override
    public List<Employee> loadAllEmployee() throws UserManagementException {
        try {
            return loadEmployeePort.loadAllEmployee();
        } catch (PersistenceAdapterException e) {
            throw new UserManagementException(e.getMessage(), e);
        }
    }
}
