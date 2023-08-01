package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.DeleteEmployeeUseCase;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteEmployeeController {
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;

    public DeleteEmployeeController(DeleteEmployeeUseCase deleteEmployeeUseCase) {
        this.deleteEmployeeUseCase = deleteEmployeeUseCase;
    }

    @DeleteMapping("/v1/employee/save")
    public void delete(@RequestBody DeleteEmployeeCommand command) {
        try {
            deleteEmployeeUseCase.deleteEmployee(command.id());
        } catch (UserManagementException e) {
            e.printStackTrace();
        }
    }
}
