package com.baga.promon.usermanagement.adapter.port.out;

import static org.assertj.core.api.Assertions.assertThat;

import com.baga.promon.usermanagement.application.port.out.SaveEmployeeEntityPort;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveEmployeeEntityPortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private SaveEmployeeEntityPort saveEmployeeEntityPort;

    @BeforeEach
    void initialSetup() {
        saveEmployeeEntityPort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void saveEntitySuccess() throws Exception {
        Employee employee = new Employee(null, "address", "name", LocalDateTime.now());
        when(employeesRepository.save(employee)).thenReturn(1L);
        Long saveResult = saveEmployeeEntityPort.saveEntity(employee);

        assertThat(saveResult).isEqualTo(1L);
    }

    @Test
    void saveEntity() throws RepositoryImplementationException {
        when(employeesRepository.save(null)).thenThrow(RepositoryImplementationException.class);
        assertThatThrownBy(() -> saveEmployeeEntityPort.saveEntity(null))
                .isInstanceOf(PersistenceAdapterException.class);
    }
}
