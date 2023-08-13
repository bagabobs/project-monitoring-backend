package com.baga.promon.usermanagement.adapter.port.out;

import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static org.assertj.core.api.Assertions.*;

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
        Long saveId = insertEmployee(address, "name");

        String updatedName = "updatedn name";
        Employee updatedEmployee = new Employee(new BigDecimal(saveId), address, updatedName, LocalDateTime.now());
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
    void deleteDataEmptyWhenSelected() throws Exception {
        Long saveId = insertEmployee("address", "name");

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
    void findAllWillGetAllData() throws Exception {
        List<Long> insertedIds = insertBundle(2);


        List<Employee> employees = repository.findAll();
        assertThat(employees.size()).isEqualTo(2);
        assertThat(employees.get(0).id().longValue()).isIn(insertedIds);
        assertThat(employees.get(1).id().longValue()).isIn(insertedIds);
    }

    @Test
    void findAfterIdWillGetDataEqualToLimitSize() throws Exception {
        List<Long> ids = insertBundle(100);
        List<Long> firstPageId = ids.stream().limit(10).toList();

        List<Long> idsFromRepo = repository.findAfterId(0L, 10).stream()
                .map(val -> val.id().longValue()).toList();

        assertThat(idsFromRepo).isEqualTo(firstPageId);

        List<Long> secondPage = ids.stream().skip(10).limit(10).toList();

        List<Long> idsFromRepoSecondPage = repository.findAfterId(idsFromRepo.get(9), 10).stream()
                .map(val -> val.id().longValue()).toList();
        assertThat(idsFromRepoSecondPage).isEqualTo(secondPage);
    }

    private Long insertEmployee(String address, String name) throws RepositoryImplementationException {
        Employee employee = new Employee(null, address, name, LocalDateTime.now());
        return repository.save(employee);
    }

    private List<Long> insertBundle(int size) throws RepositoryImplementationException {
        List<Long> ids = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Long saveId = insertEmployee("address"+i, "name"+i);
            ids.add(saveId);
        }
        return ids;
    }
}
