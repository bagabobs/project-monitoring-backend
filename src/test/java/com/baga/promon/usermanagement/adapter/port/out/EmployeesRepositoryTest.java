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
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void updateEmployeeHaveSameDataWithSelectQuery() throws RepositoryImplementationException {
        String address = "address";
        String name = "name";
        LocalDateTime offsetDateTimeNow = LocalDateTime.now();
        Employee employee = new Employee(null, address, name, offsetDateTimeNow);
        Long saveId = repository.save(employee);

        String updatedName = "updatedn name";
        Employee updatedEmployee = new Employee(new BigDecimal(saveId), address, updatedName, offsetDateTimeNow);
        Long result = repository.update(updatedEmployee);

        EmployeesRecord record = context.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(BigDecimal.valueOf(saveId))).fetchOne();
        assertThat(record).isNotNull();
        assertThat(result).isEqualTo(saveId);
        assertThat(record.getAddress()).isEqualTo(address);
        assertThat(record.getName()).isEqualTo(updatedName);
    }

    @Test
    void updateEmployeeWhenEmployeeIsEmptyThrowsException() {
        assertThatThrownBy(() -> repository.update(null))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenIdInEmployeeIsEmptyThenThrowException() {
        assertThatThrownBy(() -> {
            Employee employee = new Employee(null, "Address", "name", LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("ID in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenNameInEmployeeIsEmptyThenThrowException() {
        assertThatThrownBy(() -> {
            Employee employee = new Employee(new BigDecimal(1), "address", null, LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Name in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenAddressIsEmptyThenThrowException() {
        assertThatThrownBy(() -> {
            Employee employee = new Employee(new BigDecimal(1), null, "name", LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Address in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenJoinDateInEmployeeIsEmptyThenThrowsException() {
        assertThatThrownBy(() -> {
            Employee employee = new Employee(null, "Address", "name", null);
            repository.save(employee);
        })

                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Join Date in Employee cannot be empty");
    }

    @Test
    void deleteEmployeeDataEmptyWhenSelected() throws Exception {
        String address = "address";
        String name = "name";
        LocalDateTime offsetDateTimeNow = LocalDateTime.now();
        Employee employee = new Employee(null, address, name, offsetDateTimeNow);
        Long saveId = repository.save(employee);

        EmployeesRecord record = context.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(BigDecimal.valueOf(saveId))).fetchOne();
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(BigDecimal.valueOf(saveId));

        repository.delete(saveId);

        record = context.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(BigDecimal.valueOf(saveId))).fetchOne();
        assertThat(record).isNull();
    }

    @Test
    void findAllEmployeeWillGetAllData() throws Exception {
        String address = "address";
        String name = "name";
        LocalDateTime offsetDateTimeNow = LocalDateTime.now();
        Employee employee = new Employee(null, address, name, offsetDateTimeNow);
        Long saveId = repository.save(employee);

        String address2 = "address2";
        String name2 = "name2";
        LocalDateTime localDateTimeNow2 = LocalDateTime.now();
        Employee employee2 = new Employee(null, address2, name2, localDateTimeNow2);
        Long saveId2 = repository.save(employee2);

        List<EmployeesRecord> employeesRecords = context.selectFrom(EMPLOYEES).fetch().into(EmployeesRecord.class);
        assertThat(employeesRecords.size()).isEqualTo(2);

        List<BigDecimal> bigDecimals = new ArrayList<>();
        bigDecimals.add(BigDecimal.valueOf(saveId));
        bigDecimals.add(BigDecimal.valueOf(saveId2));

        List<Employee> employees = repository.findAll();
        assertThat(employees.size()).isEqualTo(2);
        assertThat(employees.get(0).id()).isIn(bigDecimals);
        assertThat(employees.get(1).id()).isIn(bigDecimals);
    }
}
