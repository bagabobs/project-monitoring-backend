package com.baga.promon.usermanagement.application.service;

import com.baga.promon.usermanagement.adapter.port.in.SaveEmployeeCommand;
import com.baga.promon.usermanagement.application.port.in.SaveEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.stereotype.Service;

import static com.baga.promon.usermanagement.util.UserManagementUtils.convertDateToLocalDateTime;

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

            Employee employee = new Employee(null, command.address(), command.name(),
                    convertDateToLocalDateTime(command.joinDate()));
            saveEmployeeEntityPort.saveEntity(employee);
        } catch (PersistenceAdapterException e) {
            throw new UserManagementException(e.getMessage(), e);
        }
    }

    private void validateSaveEmployeeCommand(SaveEmployeeCommand command) throws UserManagementException {
        if (command == null) {
            throw new UserManagementException("SaveEmployeeCommand cannot be empty");
        }
        if (command.address() == null) {
            throw new UserManagementException("Address cannot be empty");
        }
        if (command.name() == null) {
            throw new UserManagementException("Name cannot be empty");
        }
        if (command.joinDate() == null) {
            throw new UserManagementException("Join Date cannot be empty");
        }
    }
}
