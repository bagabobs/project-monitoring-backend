package com.baga.promon.usermanagement.application.port.out;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;

public interface SaveEmployeePort {
    long saveEntity(Employee employee) throws PersistenceAdapterException;
}
