package com.baga.promon.usermanagement.application.port.out;

import com.baga.promon.usermanagement.util.PersistenceAdapterException;

public interface DeleteEmployeePort {
    Long deleteEntity(Long id) throws PersistenceAdapterException;
}
