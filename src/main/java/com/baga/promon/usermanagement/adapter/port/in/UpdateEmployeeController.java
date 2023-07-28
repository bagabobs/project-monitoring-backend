package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.UpdateEmployeeUseCase;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateEmployeeController {
    private final UpdateEmployeeUseCase updateEmployeeUseCase;

    public UpdateEmployeeController(UpdateEmployeeUseCase updateEmployeeUseCase) {
        this.updateEmployeeUseCase = updateEmployeeUseCase;
    }

    @PutMapping("/v1/employee/update")
    public void update(@RequestBody UpdateEmployeeCommand updateEmployeeCommand) {
        try {
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
        } catch(UserManagementException exception) {
            exception.printStackTrace();
        }
    }
}
