package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.util.UserManagementException;

public interface DeleteEmployeeUseCase {
    void deleteEmployee(Long id) throws UserManagementException;
}
