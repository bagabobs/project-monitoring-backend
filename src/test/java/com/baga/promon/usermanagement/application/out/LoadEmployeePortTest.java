package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoadEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private LoadEmployeePort loadEmployeePort;

    @BeforeEach
    void initialSetup() {
        loadEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void loadAllEmployeeGetAllDataSize() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(BigDecimal.valueOf(1L), "address", "name", LocalDateTime.now()));
        employees.add(new Employee(BigDecimal.valueOf(2L), "address2", "name2", LocalDateTime.now()));
        when(loadEmployeePort.findAllEmployee()).thenReturn(employees);

        List<Employee> employeeListResult = loadEmployeePort.findAllEmployee();

        assertThat(employeeListResult.size()).isEqualTo(employees.size());
        verify(employeesRepository).findAll();
    }
}
