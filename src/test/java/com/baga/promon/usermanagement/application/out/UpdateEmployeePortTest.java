package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
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

@ExtendWith(MockitoExtension.class)
class UpdateEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private UpdateEmployeePort updateEmployeePort;

    @BeforeEach
    void initialSetup() {
        updateEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void updateEntitySuccess() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(new BigDecimal(1), "name", "address",
                LocalDateTime.now());
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.update(employeeEntity)).thenReturn(1L);
        long result = updateEmployeePort.updateEntity(employee);
        verify(employeesRepository).update(employeeEntity);
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void updateEntityWhenEmployeeNullThenThrowException() throws Exception {
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(null))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Employee cannot be empty");
    }

    @Test
    void updateEntityWhenNameInEmployeeIsEmptyThenThrowException() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(new BigDecimal(1), null, "address",
                LocalDateTime.now());
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.update(employeeEntity)).thenThrow(
                new RepositoryImplementationException("Name in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Name in Employee cannot be empty");
        verify(employeesRepository).update(employeeEntity);
    }

    @Test
    void updateEntityWhenAddressInEmployeeIsEmptyThenThrowException() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(new BigDecimal(1), "name", null,
                LocalDateTime.now());
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.update(employeeEntity)).thenThrow(
                new RepositoryImplementationException("Address in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Address in Employee cannot be empty");
        verify(employeesRepository).update(employeeEntity);
    }

    @Test
    void updateEntityWhenJoinDateInEmployeeIsEmptyThenThrowException() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(new BigDecimal(1), "name", "address",
                null);
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.update(employeeEntity)).thenThrow(
                new RepositoryImplementationException("Join Date in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("Join Date in Employee cannot be empty");
        verify(employeesRepository).update(employeeEntity);
    }

    @Test
    void updateEntityWhenIdInEmployeeIsEmptyThenThrowException() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(null, "name", "address",
                LocalDateTime.now());
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.update(employeeEntity)).thenThrow(
                new RepositoryImplementationException("ID in Employee cannot be empty"));
        assertThatThrownBy(() -> updateEmployeePort.updateEntity(employee))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("ID in Employee cannot be empty");
        verify(employeesRepository).update(employeeEntity);
    }
}
