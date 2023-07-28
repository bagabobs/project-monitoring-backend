package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
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

@ExtendWith(MockitoExtension.class)
public class UpdateEmployeeEntityPortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private UpdateEmployeePort updateEmployeePort;

    @BeforeEach
    void initialSetup() {
        updateEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void updateEntitySuccess() throws Exception {
        Employee employee = new Employee(new BigDecimal(1), "address", "name", LocalDateTime.now());
        when(employeesRepository.update(employee)).thenReturn(1L);
        long result = updateEmployeePort.updateEntity(employee);
        verify(employeesRepository).update(employee);
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void updateEntityWhenEmployeeNullThenThrowException() throws Exception {
        when(employeesRepository.update(null)).thenThrow(
                new RepositoryImplementationException("Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(null))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Employee cannot be empty");
        verify(employeesRepository).update(null);
    }

    @Test
    void updateEntityWhenNameInEmployeeIsEmptyThenThrowException() throws Exception {
        Employee employee = new Employee(new BigDecimal(1), "address", null, LocalDateTime.now());
        when(employeesRepository.update(employee)).thenThrow(
                new RepositoryImplementationException("Name in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Name in Employee cannot be empty");
        verify(employeesRepository).update(employee);
    }

    @Test
    void updateEntityWhenAddressInEmployeeIsEmptyThenThrowException() throws Exception {
        Employee employee = new Employee(new BigDecimal(1), null, "name", LocalDateTime.now());
        when(employeesRepository.update(employee)).thenThrow(
                new RepositoryImplementationException("Address in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Address in Employee cannot be empty");
        verify(employeesRepository).update(employee);
    }

    @Test
    void updateEntityWhenJoinDateInEmployeeIsEmptyThenThrowException() throws Exception {
        Employee employee = new Employee(new BigDecimal(1), "address", "name", null);
        when(employeesRepository.update(employee)).thenThrow(
                new RepositoryImplementationException("Join Date in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Join Date in Employee cannot be empty");
        verify(employeesRepository).update(employee);
    }

    @Test
    void updateEntityWhenIdInEmployeeIsEmptyThenThrowException() throws Exception {
        Employee employee = new Employee(null, "address", "name", LocalDateTime.now());
        when(employeesRepository.update(employee)).thenThrow(
                new RepositoryImplementationException("ID in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("ID in Employee cannot be empty");
        verify(employeesRepository).update(employee);
    }
}
