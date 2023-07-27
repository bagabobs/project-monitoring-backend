package com.baga.promon.usermanagement.application.service;
import static com.baga.promon.usermanagement.util.UserManagementUtils.convertDateToLocalDateTime;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.baga.promon.usermanagement.application.port.in.SaveEmployeeCommand;
import com.baga.promon.usermanagement.application.port.in.SaveEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class SaveEmployeeUseCaseTest {
    @Mock
    private SaveEmployeeEntityPort saveEmployeeEntityPort;
    private SaveEmployeeUseCase saveEmployeeUseCase;

    @BeforeEach
    void initialSetup() {
        saveEmployeeUseCase = new SaveEmployeeService(saveEmployeeEntityPort);
    }

    @Test
    void saveEmployeeNotThrownExceptionWhenSuccess() {
        assertThatNoException().isThrownBy(() -> {
            SaveEmployeeCommand saveEmployeeCommand = new SaveEmployeeCommand("name", "address",
                    new Date());
            Employee employee = new Employee(null, saveEmployeeCommand.address(), saveEmployeeCommand.name(),
                    convertDateToLocalDateTime(saveEmployeeCommand.joinDate()));
            when(saveEmployeeEntityPort.saveEntity(employee)).thenReturn(1L);
            saveEmployeeUseCase.saveEmployee(saveEmployeeCommand);
            verify(saveEmployeeEntityPort).saveEntity(employee);
        });
    }

    @Test
    void saveEmployeeThrowsExceptionWhenCommandIsNull() {
        assertThatThrownBy(() -> saveEmployeeUseCase.saveEmployee(null))
                .isInstanceOf(UserManagementException.class)
                .hasMessage("SaveEmployeeCommand cannot be empty");
    }

    @Test
    void saveEmployeeThrowsExceptionWhenAddressIsNull() {
        assertThatThrownBy(() -> {
            SaveEmployeeCommand command = new SaveEmployeeCommand("name", null, new Date());
            saveEmployeeUseCase.saveEmployee(command);
        }).isInstanceOf(UserManagementException.class)
                .hasMessage("Address cannot be empty");
    }

    @Test
    void saveEmployeeThrowsExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> {
            SaveEmployeeCommand command = new SaveEmployeeCommand(null, "address", new Date());
            saveEmployeeUseCase.saveEmployee(command);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("Name cannot be empty");
    }

    @Test
    void saveEmployeeThrowsExceptionWhenJoinDateIsNull() {
        assertThatThrownBy(() -> {
            SaveEmployeeCommand command = new SaveEmployeeCommand("name", "address", null);
            saveEmployeeUseCase.saveEmployee(command);
        })
                .isInstanceOf(UserManagementException.class)
                .hasMessage("Join Date cannot be empty");
    }
}
