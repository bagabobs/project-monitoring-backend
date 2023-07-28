package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.adapter.port.in.UpdateEmployeeCommand;
import com.baga.promon.usermanagement.util.UserManagementException;

public interface UpdateEmployeeUseCase {
    void updateEmployee(UpdateEmployeeCommand updateEmployeeCommand) throws UserManagementException;
}
