package com.baga.promon.usermanagement.application.service;

import com.baga.promon.usermanagement.adapter.port.in.UpdateEmployeeCommand;
import com.baga.promon.usermanagement.application.port.in.UpdateEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.baga.promon.usermanagement.util.UserManagementUtils.convertDateToLocalDateTime;

@Service
public class UpdateEmployeeService implements UpdateEmployeeUseCase {
    private final UpdateEmployeePort updateEmployeePort;

    public UpdateEmployeeService(UpdateEmployeePort updateEmployeePort) {
        this.updateEmployeePort = updateEmployeePort;
    }


    @Override
    public void updateEmployee(UpdateEmployeeCommand updateEmployeeCommand) throws UserManagementException {
        try {
            validateUpdateEmployeeCommand(updateEmployeeCommand);

            Employee employee = new Employee(new BigDecimal(updateEmployeeCommand.id()),
                    updateEmployeeCommand.address(), updateEmployeeCommand.name(),
                    convertDateToLocalDateTime(updateEmployeeCommand.joinDate()));
            updateEmployeePort.updateEntity(employee);
        } catch(PersistenceAdapterException exception) {
            throw new UserManagementException(exception.getMessage(), exception);
        }
    }

    private void validateUpdateEmployeeCommand(UpdateEmployeeCommand updateEmployeeCommand)
            throws UserManagementException{
        if (updateEmployeeCommand == null) {
            throw new UserManagementException("UpdateEmployeeCommand cannot be empty");
        }

        if (updateEmployeeCommand.id() == null) {
            throw new UserManagementException("ID in UpdateEmployeeCommand cannot be empty");
        }

        if (updateEmployeeCommand.name() == null) {
            throw new UserManagementException("Name in UpdateEmployeeCommand cannot be empty");
        }

        if (updateEmployeeCommand.address() == null) {
            throw new UserManagementException("Address in UpdateEmployeeCommand cannot be empty");
        }

        if (updateEmployeeCommand.joinDate() == null) {
            throw new UserManagementException("Join Date in UpdateEmployeeCommand cannot be empty");
        }
    }
}
