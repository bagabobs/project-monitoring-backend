package com.baga.promon.usermanagement.application.service;

import com.baga.promon.usermanagement.application.port.in.SaveEmployeeCommand;
import com.baga.promon.usermanagement.application.port.in.SaveEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.stereotype.Service;

@Service
public class SaveEmployeeService implements SaveEmployeeUseCase {
    private final SaveEmployeeEntityPort saveEmployeeEntityPort;

    public SaveEmployeeService(SaveEmployeeEntityPort saveEmployeeEntityPort) {
        this.saveEmployeeEntityPort = saveEmployeeEntityPort;
    }

    @Override
    public void saveEmployee(SaveEmployeeCommand command) throws UserManagementException {
        try {
            validateSaveEmployeeCommand(command);

            Employee employee = new Employee(null, command.getAddress(), command.getName(), command.getJoinDate());
            saveEmployeeEntityPort.saveEntity(employee);
        } catch (PersistenceAdapterException e) {
            throw new UserManagementException(e.getMessage(), e);
        }
    }

    void validateSaveEmployeeCommand(SaveEmployeeCommand command) throws UserManagementException {
        if (command == null) {
            throw new UserManagementException("SaveEmployeeCommand cannot be empty");
        }
        if (command.getAddress() == null) {
            throw new UserManagementException("Address cannot be empty");
        }
        if (command.getName() == null) {
            throw new UserManagementException("Name cannot be empty");
        }
        if (command.getJoinDate() == null) {
            throw new UserManagementException("Join Date cannot be empty");
        }
    }
}
