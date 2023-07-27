package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import org.springframework.stereotype.Component;

@Component
public class EmployeePersistenceAdapter implements SaveEmployeeEntityPort, UpdateEmployeePort {
    private final EmployeesRepository employeesRepository;

    public EmployeePersistenceAdapter(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }


    @Override
    public long saveEntity(Employee entity) throws PersistenceAdapterException {
        try {
            return employeesRepository.save(entity);
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }

    @Override
    public long updateEntity(Employee employee) throws PersistenceAdapterException {
        try {
            return employeesRepository.update(employee);
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }
}
