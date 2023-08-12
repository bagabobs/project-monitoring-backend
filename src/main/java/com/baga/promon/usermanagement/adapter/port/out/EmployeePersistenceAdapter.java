package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.application.port.out.DeleteEmployeePort;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeePort;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeePersistenceAdapter implements SaveEmployeePort, UpdateEmployeePort, DeleteEmployeePort,
        LoadEmployeePort {
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

    @Override
    public Long deleteEntity(Long id) throws PersistenceAdapterException {
        try {
            return employeesRepository.delete(id);
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<Employee> findAllEmployee() throws PersistenceAdapterException {
        try {
            return employeesRepository.findAll();
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }
}
