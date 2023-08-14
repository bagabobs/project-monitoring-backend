package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
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
        List<Employee> employees = createEmployeeList(2);
        when(employeesRepository.findAll()).thenReturn(employees);

        List<Employee> employeeListResult = loadEmployeePort.loadAllEmployee();

        assertThat(employeeListResult.size()).isEqualTo(employees.size());
        verify(employeesRepository).findAll();
    }

    @Test
    void loadEmployeeAfterIdWillGetDataEqualToLimitSize() throws Exception {
        var employees = createEmployeeList(10);
        when(employeesRepository.findAfterId(0L, 10)).thenReturn(employees);

        List<Employee> employeesFromRepo = loadEmployeePort.loadEmployeeAfterId(0L, 10);
        verify(employeesRepository).findAfterId(0L, 10);
        assertThat(employeesFromRepo).isEqualTo(employees);
    }

    @Test
    void loadEmployeeAfterIdWhenIdNullThenThrowException() throws Exception {
        when(employeesRepository.findAfterId(null, 2)).thenThrow(
                new RepositoryImplementationException("ID cannot be empty"));

        assertThatThrownBy(() -> loadEmployeePort.loadEmployeeAfterId(null, 2))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("ID cannot be empty");
    }

    @Test
    void loadEmployeeAfterIdWhenSizeIsLessThen1ThenThrowException() throws Exception {
        when(employeesRepository.findAfterId(0L, 0)).thenThrow(
                new RepositoryImplementationException("Size cannot be less than 1"));

        assertThatThrownBy(() -> loadEmployeePort.loadEmployeeAfterId(0L, 0))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Size cannot be less than 1");
    }

    private List<Employee> createEmployeeList(int size) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Employee employee = new Employee(BigDecimal.valueOf(i), "address"+i, "name"+i,
                    LocalDateTime.now());
            employees.add(employee);
        }
        return employees;
    }
}
