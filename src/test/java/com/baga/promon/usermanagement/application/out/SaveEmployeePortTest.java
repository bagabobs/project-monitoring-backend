package com.baga.promon.usermanagement.application.out;

import static org.assertj.core.api.Assertions.assertThat;

import com.baga.promon.usermanagement.adapter.port.out.EmployeePersistenceAdapter;
import com.baga.promon.usermanagement.adapter.port.out.EmployeesRepository;
import com.baga.promon.usermanagement.application.port.out.SaveEmployeePort;
import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.pojos.EmployeeEntity;
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
class SaveEmployeePortTest {
    @Mock
    private EmployeesRepository employeesRepository;
    private SaveEmployeePort saveEmployeePort;

    @BeforeEach
    void initialSetup() {
        saveEmployeePort = new EmployeePersistenceAdapter(employeesRepository);
    }

    @Test
    void saveEntitySuccess() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(null, "name", "address",
                LocalDateTime.now());
        Employee employee = new Employee(employeeEntity.getId(), employeeEntity.getAddress(), employeeEntity.getName(),
                employeeEntity.getJoinDate());
        when(employeesRepository.save(employeeEntity)).thenReturn(1L);
        Long saveResult = saveEmployeePort.saveEntity(employee);
        verify(employeesRepository).save(employeeEntity);

        assertThat(saveResult).isEqualTo(1L);
    }

    @Test
    void saveEntityWhenEmployeeNullThenThrowException() throws RepositoryImplementationException {
        assertThatThrownBy(() -> saveEmployeePort.saveEntity(null))
                .isInstanceOf(PersistenceAdapterException.class);
    }
}
