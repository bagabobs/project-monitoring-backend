package com.baga.promon.usermanagement.application.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.application.service.LoadEmployeeService;
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
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class LoadEmployeeUseCaseTest {
    @Mock
    private LoadEmployeePort loadEmployeePort;
    private LoadEmployeeUseCase loadEmployeeUseCase;

    @BeforeEach
    void initialSetup() {
        loadEmployeeUseCase = new LoadEmployeeService(loadEmployeePort);
    }

    @Test
    void loadAllEmployeeGetTheSameSize() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(BigDecimal.valueOf(1L), "address", "name", LocalDateTime.now()));
        employees.add(new Employee(BigDecimal.valueOf(2L), "address2", "name2", LocalDateTime.now()));
        when(loadEmployeePort.loadAllEmployee()).thenReturn(employees);

        List<BigDecimal> bigDecimals = employees.stream().map(Employee::id).collect(Collectors.toList());

        List<Employee> resultEmployees = loadEmployeeUseCase.loadAllEmployee();
        verify(loadEmployeePort).loadAllEmployee();

        assertThat(resultEmployees.size()).isEqualTo(employees.size());
        assertThat(resultEmployees.get(0).id()).isIn(bigDecimals);
        assertThat(resultEmployees.get(1).id()).isIn(bigDecimals);
    }
}
