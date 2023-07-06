package com.baga.promon.usermanagement.application.port.out;

import com.baga.promon.usermanagement.domain.Employee;

import static com.baga.promon.usermanagement.generated.tables.Employees.EMPLOYEES;

public interface SaveEmployeeEntityPort {
        long saveEntity(Employee entity);
}
