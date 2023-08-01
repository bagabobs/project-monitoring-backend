package com.baga.promon.usermanagement.application.service;

import com.baga.promon.usermanagement.application.port.in.DeleteEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.DeleteEmployeePort;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.stereotype.Service;

@Service
public class DeleteEmployeeService implements DeleteEmployeeUseCase {
    private final DeleteEmployeePort deleteEmployeePort;

    public DeleteEmployeeService(DeleteEmployeePort deleteEmployeePort) {
        this.deleteEmployeePort = deleteEmployeePort;
    }

    @Override
    public void deleteEmployee(Long id) throws UserManagementException {
        validateId(id);

        try {
            deleteEmployeePort.deleteEntity(id);
        } catch(PersistenceAdapterException exception) {
            throw new UserManagementException(exception.getMessage(), exception);
        }
    }

    private void validateId(Long id) throws UserManagementException {
        if (id == null) {
            throw new UserManagementException("ID cannot be empty");
        }
    }
}
