package com.baga.promon.usermanagement.application.service;

import static com.baga.promon.usermanagement.util.UserManagementUtils.convertDateToLocalDateTime;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.adapter.port.in.UpdateEmployeeCommand;
import com.baga.promon.usermanagement.application.port.in.UpdateEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.UpdateEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class UpdateEmployeeUseCaseTest {
    @Mock
    private UpdateEmployeePort updateEmployeePort;
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    @BeforeEach
    void initialSetup() {
        updateEmployeeUseCase = new UpdateEmployeeService(updateEmployeePort);
    }

    @Test
    void updateEmployeeWhenCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> updateEmployeeUseCase.updateEmployee(null))
                .isInstanceOf(UserManagementException.class)
                .hasMessage("UpdateEmployeeCommand cannot be empty");
    }

    @Test
    void updateEmployeeWhenIdInCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> {
            UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(null, "name",
                    "address", new Date());
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("ID in UpdateEmployeeCommand cannot be empty");
    }

    @Test
    void updateEmployeeWhenNameInCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> {
            UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(1L, null,
                    "address", new Date());
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("Name in UpdateEmployeeCommand cannot be empty");
    }

    @Test
    void updateEmployeeWhenAddressInCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> {
            UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(1L, "name", null,
                    new Date());
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("Address in UpdateEmployeeCommand cannot be empty");
    }

    @Test
    void updateEmployeeWhenJoinDateInCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> {
            UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(1L, "name",
                    "address", null);
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("Join Date in UpdateEmployeeCommand cannot be empty");
    }

    @Test
    void updateEmployeeNotThrownExceptionWhenSuccess() {
        assertThatNoException().isThrownBy(() -> {
            UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(1L, "name",
                    "address", new Date());
            Employee employee = new Employee(new BigDecimal(updateEmployeeCommand.id()),
                    updateEmployeeCommand.address(), updateEmployeeCommand.name(),
                    convertDateToLocalDateTime(updateEmployeeCommand.joinDate()));
            when(updateEmployeePort.updateEntity(employee)).thenReturn(updateEmployeeCommand.id());
            updateEmployeeUseCase.updateEmployee(updateEmployeeCommand);
            verify(updateEmployeePort).updateEntity(employee);
        });
    }
}