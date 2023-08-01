package com.baga.promon.usermanagement.adapter.port.in;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteEmployeeController {

    @DeleteMapping("/v1/employee/save")
    public void delete(Long id) {
    }
}
