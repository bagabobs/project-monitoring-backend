package com.baga.promon.usermanagement.application.out;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.DeleteEmployeePort;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private DeleteEmployeePort deleteEmployeePort;

    @BeforeEach
    void initialSetup() {
        deleteEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void deleteEmployeeSuccess() throws Exception {
        Long id = 1L;
        when(deleteEmployeePort.deleteEntity(id)).thenReturn(id);
        long resultId = deleteEmployeePort.deleteEntity(id);
        verify(employeesRepository).delete(id);
        assertThat(resultId).isEqualTo(id);
    }

    @Test
    void deleteEntityWhenIdIsNullThenThrowException() throws RepositoryImplementationException {
        when(employeesRepository.delete(null)).thenThrow(new RepositoryImplementationException("ID cannot be empty"));
        assertThatThrownBy(() -> deleteEmployeePort.deleteEntity(null))
                .isInstanceOf(PersistenceAdapterException.class)
                .hasMessage("ID cannot be empty");
        verify(employeesRepository).delete(null);
    }
}
