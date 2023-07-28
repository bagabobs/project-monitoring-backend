package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.adapter.port.in.SaveEmployeeCommand;
import com.baga.promon.usermanagement.util.UserManagementException;

public interface SaveEmployeeUseCase {
    void saveEmployee(SaveEmployeeCommand command) throws UserManagementException;
}
