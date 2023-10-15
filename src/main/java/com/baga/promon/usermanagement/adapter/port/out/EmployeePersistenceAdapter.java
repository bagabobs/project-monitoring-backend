package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.application.port.out.DeleteEmployeePort;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeePort;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.pojos.EmployeeEntity;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
            if (entity == null) {
                throw new PersistenceAdapterException("Employee cannot be empty");
            }
            EmployeeEntity employeeEntity = new EmployeeEntity(entity.id(), entity.name(), entity.address(),
                    entity.joinDate());
            return employeesRepository.save(employeeEntity);
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }

    @Override
    public long updateEntity(Employee employee) throws PersistenceAdapterException {
        try {
            if (employee == null) {
                throw new PersistenceAdapterException("Employee cannot be empty");
            }
            EmployeeEntity employeeEntity = new EmployeeEntity(employee.id(), employee.name(), employee.address(),
                    employee.joinDate());
            return employeesRepository.update(employeeEntity);
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
    public List<Employee> loadAllEmployee() throws PersistenceAdapterException {
        try {
            return employeesRepository.findAll().stream().map(employeeEntity -> new Employee(employeeEntity.getId(),
                    employeeEntity.getAddress(), employeeEntity.getName(), employeeEntity.getJoinDate()))
                    .toList();
        } catch(RepositoryImplementationException exception) {
            throw new PersistenceAdapterException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<Employee> loadEmployeeAfterId(Long id, int size) throws PersistenceAdapterException {
        try {
            return employeesRepository.findAfterId(id, size)
                    .stream()
                    .map(employeeEntity -> new Employee(employeeEntity.getId(), employeeEntity.getAddress(),
                            employeeEntity.getName(), employeeEntity.getJoinDate()))
                    .toList();
        } catch(RepositoryImplementationException e) {
            throw new PersistenceAdapterException(e.getMessage(), e);
        }

    }

    @Override
    public Optional<Employee> loadEmployeeById(Long id) throws PersistenceAdapterException {
        try {
            Optional<EmployeeEntity> employeeEntityOptional = employeesRepository.findById(id);
            if (employeeEntityOptional.isEmpty()) {
                return Optional.empty();
            }
            EmployeeEntity employeeEntity = employeeEntityOptional.get();
            return Optional.of(new Employee(employeeEntity.getId(), employeeEntity.getAddress(),
                    employeeEntity.getName(), employeeEntity.getJoinDate()));
        } catch(RepositoryImplementationException e) {
            throw new PersistenceAdapterException(e.getMessage(), e);
        }
    }
}
