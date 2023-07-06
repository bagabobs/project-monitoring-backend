package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
import com.baga.promon.usermanagement.domain.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeePersistenceAdapter implements SaveEmployeeEntityPort {
        @Override
        public long saveEntity(Employee entity) {

            return 0;
        }
}
