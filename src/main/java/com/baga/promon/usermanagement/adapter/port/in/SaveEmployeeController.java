package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.SaveEmployeeUseCase;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaveEmployeeController {
    private final SaveEmployeeUseCase saveEmployeeUseCase;

    public SaveEmployeeController(SaveEmployeeUseCase saveEmployeeUseCase) {
        this.saveEmployeeUseCase = saveEmployeeUseCase;
    }

    @PostMapping("/v1/employee/save")
    public void save(@RequestBody SaveEmployeeCommand saveEmployeeCommand) {
        try {
            saveEmployeeUseCase.saveEmployee(saveEmployeeCommand);
        } catch (UserManagementException e) {
            e.printStackTrace();
        }
    }
}
