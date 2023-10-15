package com.baga.promon.usermanagement.application.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class LoadEmployeeUseCaseTest {
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

        assertThat(resultEmployees).hasSameSizeAs(employees);
        assertThat(resultEmployees.get(0).id()).isIn(bigDecimals);
        assertThat(resultEmployees.get(1).id()).isIn(bigDecimals);
    }

    @Test
    void loadEmployeeByPageGetTheSameSize() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(BigDecimal.valueOf(1L), "address", "name", LocalDateTime.now()));
        employees.add(new Employee(BigDecimal.valueOf(2L), "address2", "name2", LocalDateTime.now()));
        when(loadEmployeePort.loadEmployeeAfterId(0L, 2)).thenReturn(employees);

        List<BigDecimal> bigDecimals = employees.stream().map(Employee::id).toList();

        List<Employee> resultEmployees = loadEmployeeUseCase.loadEmployeeByPage(0L, 2);
        verify(loadEmployeePort).loadEmployeeAfterId(0L, 2);

        assertThat(resultEmployees).hasSameSizeAs(employees);
        assertThat(resultEmployees.get(0).id()).isIn(bigDecimals);
        assertThat(resultEmployees.get(1).id()).isIn(bigDecimals);
    }

    @Test
    void loadEmployeeByIdGetTheSameSize() throws Exception {
        Employee employee = new Employee(BigDecimal.valueOf(1L), "address", "name", LocalDateTime.now());
        when(loadEmployeePort.loadEmployeeById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> employeeOptional = loadEmployeeUseCase.loadEmployeeById(1L);
        verify(loadEmployeePort, times(1)).loadEmployeeById(1L);
        assertThat(employeeOptional).isEqualTo(Optional.of(employee));
    }

    @Test
    void loadEmployeeByIdGetEmpty() throws Exception {
        when(loadEmployeePort.loadEmployeeById(1L)).thenReturn(Optional.empty());
        Optional<Employee> employeeOptional = loadEmployeeUseCase.loadEmployeeById(1L);
        verify(loadEmployeePort, times(1)).loadEmployeeById(1L);
        assertThat(employeeOptional).isEmpty();
    }
}
