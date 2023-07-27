package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.util.UserManagementException;

public interface UpdateEmployeeUseCase {
    void updateEmployee(UpdateEmployeeCommand updateEmployeeCommand) throws UserManagementException;
}
