package com.baga.promon.usermanagement.adapter.port.out;

import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.records.EmployeesRecord;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JooqTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:15.3-alpine:///db"
        })
public class EmployeesRepositoryTest {
    @Autowired
    private DSLContext context;
    private EmployeesRepository repository;

    @BeforeEach
    void initialSetup() {
        repository = new EmployeesRepositoryImpl(context);
    }

    @Test
    void saveEmployeeHaveSameDataWithSelectQuery() throws RepositoryImplementationException {
        String address = "address";
        String name = "name";
        LocalDateTime offsetDateTimeNow = LocalDateTime.now();
        Employee employee = new Employee(null, address, name, offsetDateTimeNow);
        Long saveId = repository.save(employee);


        EmployeesRecord record = context.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(BigDecimal.valueOf(saveId))).fetchOne();
        assertThat(record).isNotNull();
        assertThat(record.getAddress()).isEqualTo(address);
        assertThat(record.getName()).isEqualTo(name);
    }

    @Test
    void saveEmployeeWhenEmployeeIsEmptyThrowsException() {
        assertThatThrownBy(() -> repository.save(null))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Employee cannot be empty");
    }

    @Test
    void saveEmployeeWhenAddressInEmployeeIsEmptyThenThrowsException() {
        Employee employee = new Employee(null, null, "name", LocalDateTime.now());
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Address in Employee cannot be empty");
    }

    @Test
    void saveEmployeeWhenNameInEmployeeIsEmptyThenThrowsException() {
        Employee employee = new Employee(null, "Address", null, LocalDateTime.now());
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Name in Employee cannot be empty");
    }

    @Test
    void saveEmployeeWhenJoinDateInEmployeeIsEmptyThenThrowsException() {
        Employee employee = new Employee(null, "Address", "name", null);
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Join Date in Employee cannot be empty");
    }
}
