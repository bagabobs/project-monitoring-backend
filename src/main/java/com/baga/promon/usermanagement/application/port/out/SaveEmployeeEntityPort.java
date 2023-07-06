package com.baga.promon.usermanagement.application.port.out;

import com.baga.promon.usermanagement.domain.Employee;

public interface SaveEmployeeEntityPort {
    long saveEntity(Employee entity);
}
