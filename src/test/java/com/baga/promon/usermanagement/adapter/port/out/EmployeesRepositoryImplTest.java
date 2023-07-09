package com.baga.promon.usermanagement.adapter.port.out;

import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static org.assertj.core.api.Assertions.assertThat;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.records.EmployeesRecord;
import com.baga.promon.usermanagement.util.PersistenceRepositoryException;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JooqTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:15.3-alpine:///db"
        })
public class EmployeesRepositoryImplTest {
    @Autowired
    private DSLContext context;
    private EmployeesRepository<Long, Employee> repository;

    @BeforeEach
    void initialSetup() {
        repository = new EmployeesRepositoryImpl(context);
    }

    @Test
    void saveEmployeeHaveSameDataWithSelectQuery() throws PersistenceRepositoryException {
        String address = "address";
        String name = "name";
        OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
        Employee employee = new Employee(null, address, name, offsetDateTimeNow);
        Long saveId = repository.save(employee);


        EmployeesRecord record = context.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(BigDecimal.valueOf(saveId))).fetchOne();
        assertThat(record).isNotNull();
        assertThat(record.getAddress()).isEqualTo(address);
        assertThat(record.getName()).isEqualTo(name);
    }
}
