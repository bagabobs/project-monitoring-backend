package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import com.baga.promon.usermanagement.util.PersistenceAdapterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private SaveEmployeePort saveEmployeePort;

    @BeforeEach
    void initialSetup() {
        saveEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void saveEntitySuccess() throws Exception {
        Employee employee = new Employee(null, "address", "name", LocalDateTime.now());
        when(employeesRepository.save(employee)).thenReturn(1L);
        Long saveResult = saveEmployeePort.saveEntity(employee);
        verify(employeesRepository).save(employee);

        assertThat(saveResult).isEqualTo(1L);
    }

    @Test
    void saveEntityWhenEmployeeNullThenThrowException() throws RepositoryImplementationException {
        when(employeesRepository.save(null)).thenThrow(RepositoryImplementationException.class);
        assertThatThrownBy(() -> saveEmployeePort.saveEntity(null))
                .isInstanceOf(PersistenceAdapterException.class);
    }
}
