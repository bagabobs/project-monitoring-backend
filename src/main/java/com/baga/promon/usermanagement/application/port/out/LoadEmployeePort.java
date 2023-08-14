package com.baga.promon.usermanagement.application.port.out;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;

import java.util.List;

public interface LoadEmployeePort {
    List<Employee> loadAllEmployee() throws PersistenceAdapterException;

    List<Employee> loadEmployeeAfterId(Long id, int size) throws PersistenceAdapterException;
}
