package com.baga.promon.usermanagement.application.in;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.application.port.in.DeleteEmployeeUseCase;
import com.baga.promon.usermanagement.application.port.out.DeleteEmployeePort;
import com.baga.promon.usermanagement.application.service.DeleteEmployeeService;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteEmployeeUseCaseTest {
    @Mock
    private DeleteEmployeePort deleteEmployeePort;
    private DeleteEmployeeUseCase deleteEmployeeUseCase;

    @BeforeEach
    void initialSetup() {
        deleteEmployeeUseCase = new DeleteEmployeeService(deleteEmployeePort);
    }

    @Test
    void deleteEmployeeWhenCommandIsNullThenThrowException() {
        assertThatThrownBy(() -> deleteEmployeeUseCase.deleteEmployee(null))
                .isInstanceOf(UserManagementException.class)
                .hasMessage("ID cannot be empty");
    }

    @Test
    void deleteEmployeeNotThrownExceptionWhenSuccess() {
        assertThatNoException().isThrownBy(() -> {
            Long id = 1L;
            when(deleteEmployeePort.deleteEntity(id)).thenReturn(id);
            deleteEmployeeUseCase.deleteEmployee(1L);
            verify(deleteEmployeePort).deleteEntity(id);
        });
    }
}
