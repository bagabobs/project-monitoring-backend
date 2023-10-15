package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.LoadEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.pojos.EmployeeEntity;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoadEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private LoadEmployeePort loadEmployeePort;

    @BeforeEach
    void initialSetup() {
        loadEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void loadAllEmployeeGetAllDataSize() throws Exception {
        List<EmployeeEntity> employees = createEmployeeList(2);
        when(employeesRepository.findAll()).thenReturn(employees);

        List<Employee> employeeListResult = loadEmployeePort.loadAllEmployee();
        verify(employeesRepository).findAll();

        assertThat(employeeListResult).hasSameSizeAs(employees);
    }

    @Test
    void loadEmployeeAfterIdWillGetDataEqualToLimitSize() throws Exception {
        var employees = createEmployeeList(10);
        List<Employee> employeeList = employees.stream()
                        .map(employeeEntity -> new Employee(employeeEntity.getId(), employeeEntity.getAddress(),
                                employeeEntity.getName(), employeeEntity.getJoinDate()))
                                .toList();
        when(employeesRepository.findAfterId(0L, 10)).thenReturn(employees);

        List<Employee> employeesFromRepo = loadEmployeePort.loadEmployeeAfterId(0L, 10);
        verify(employeesRepository).findAfterId(0L, 10);
        assertThat(employeesFromRepo).isEqualTo(employeeList);
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

    @Test
    void loadEmployeeByIdWhenHasSameId() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(BigDecimal.valueOf(1L), "name", "address",
                LocalDateTime.now());
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));
        Optional<Employee> employeeOptionalResult = loadEmployeePort.loadEmployeeById(1L);
        verify(employeesRepository, times(1)).findById(1L);
        Optional<Employee> employeeOptional = Optional.of(new Employee(employeeEntity.getId(),
                employeeEntity.getAddress(), employeeEntity.getName(), employeeEntity.getJoinDate()));
        assertThat(employeeOptionalResult).isEqualTo(employeeOptional);
    }

    @Test
    void loadEmployeeByIdWhenEmployeeEntityIsEmpty() throws Exception {
        when(employeesRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Employee> employeeOptionalResult = loadEmployeePort.loadEmployeeById(1L);
        verify(employeesRepository, times(1)).findById(1L);
        assertThat(employeeOptionalResult).isEmpty();
    }

    private List<EmployeeEntity> createEmployeeList(int size) {
        List<EmployeeEntity> employees = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            EmployeeEntity employee = new EmployeeEntity(BigDecimal.valueOf(i), "name"+i, "address"+i,
                    LocalDateTime.now());
            employees.add(employee);
        }
        return employees;
    }
}
