package com.baga.promon.usermanagement.adapter.port.out;

import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEE_ENTITY;
import static org.assertj.core.api.Assertions.*;

import com.baga.promon.usermanagement.generated.tables.pojos.EmployeeEntity;
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
class EmployeesRepositoryTest {
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
        EmployeeEntity employee = new EmployeeEntity(null, name, address, offsetDateTimeNow);
        Long saveId = repository.save(employee);


        EmployeeEntity record = context.selectFrom(EMPLOYEE_ENTITY)
                .where(EMPLOYEE_ENTITY.ID.eq(BigDecimal.valueOf(saveId))).fetchOneInto(EmployeeEntity.class);
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
        EmployeeEntity employee = new EmployeeEntity(null, "name", null, LocalDateTime.now());
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Address in Employee cannot be empty");
    }

    @Test
    void saveEmployeeWhenNameInEmployeeIsEmptyThenThrowsException() {
        EmployeeEntity employee = new EmployeeEntity(null, null, "address", LocalDateTime.now());
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Name in Employee cannot be empty");
    }

    @Test
    void saveEmployeeWhenJoinDateInEmployeeIsEmptyThenThrowsException() {
        EmployeeEntity employee = new EmployeeEntity(null, "Address", "name", null);
        assertThatThrownBy(() -> repository.save(employee))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Join Date in Employee cannot be empty");
    }

    @Test
    void updateEmployeeHaveSameDataWithSelectQuery() throws RepositoryImplementationException {
        String address = "address";
        Long saveId = insertEmployee(address, "name");

        String updatedName = "updatedn name";
        EmployeeEntity updatedEmployee = new EmployeeEntity(new BigDecimal(saveId), updatedName, address, LocalDateTime.now());
        Long result = repository.update(updatedEmployee);

        EmployeeEntity record = context.selectFrom(EMPLOYEE_ENTITY)
                .where(EMPLOYEE_ENTITY.ID.eq(BigDecimal.valueOf(saveId))).fetchOneInto(EmployeeEntity.class);
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
            EmployeeEntity employee = new EmployeeEntity(null, "Address", "name", LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("ID in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenNameInEmployeeIsEmptyThenThrowException() {
        assertThatThrownBy(() -> {
            EmployeeEntity employee = new EmployeeEntity(new BigDecimal(1), null, "address", LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Name in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenAddressIsEmptyThenThrowException() {
        assertThatThrownBy(() -> {
            EmployeeEntity employee = new EmployeeEntity(new BigDecimal(1), "name", null, LocalDateTime.now());
            repository.update(employee);
        })
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Address in Employee cannot be empty");
    }

    @Test
    void updateEmployeeWhenJoinDateInEmployeeIsEmptyThenThrowsException() {
        assertThatThrownBy(() -> {
            EmployeeEntity employee = new EmployeeEntity(null, "Address", "name", null);
            repository.save(employee);
        })

                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Join Date in Employee cannot be empty");
    }

    @Test
    void deleteDataEmptyWhenSelected() throws Exception {
        Long saveId = insertEmployee("address", "name");

        EmployeeEntity record = context.selectFrom(EMPLOYEE_ENTITY)
                .where(EMPLOYEE_ENTITY.ID.eq(BigDecimal.valueOf(saveId))).fetchOneInto(EmployeeEntity.class);
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(BigDecimal.valueOf(saveId));

        repository.delete(saveId);

        record = context.selectFrom(EMPLOYEE_ENTITY)
                .where(EMPLOYEE_ENTITY.ID.eq(BigDecimal.valueOf(saveId))).fetchOneInto(EmployeeEntity.class);
        assertThat(record).isNull();
    }

    @Test
    void findAllWillGetAllData() throws Exception {
        List<Long> insertedIds = insertBundle(2);


        List<EmployeeEntity> employees = repository.findAll();
        assertThat(employees).hasSize(2);
        assertThat(employees.get(0).getId().longValue()).isIn(insertedIds);
        assertThat(employees.get(1).getId().longValue()).isIn(insertedIds);
    }

    @Test
    void findAfterIdWillGetDataEqualToLimitSize() throws Exception {
        List<Long> ids = insertBundle(100);
        List<Long> firstPageId = ids.stream().limit(10).toList();

        List<Long> idsFromRepo = repository.findAfterId(0L, 10).stream()
                .map(val -> val.getId().longValue()).toList();

        assertThat(idsFromRepo).isEqualTo(firstPageId);

        List<Long> secondPage = ids.stream().skip(10).limit(10).toList();

        List<Long> idsFromRepoSecondPage = repository.findAfterId(idsFromRepo.get(9), 10).stream()
                .map(val -> val.getId().longValue()).toList();
        assertThat(idsFromRepoSecondPage).isEqualTo(secondPage);
    }

    @Test
    void findAfterIdWhenIdEmptyThenThrowsException() {
        assertThatThrownBy(() -> repository.findAfterId(null, 10))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("ID cannot be empty");
    }

    @Test
    void findAfterIdWhenSizeIsLessThen1TheThrowsException() {
        assertThatThrownBy(() -> repository.findAfterId(1L, 0))
                .isInstanceOf(RepositoryImplementationException.class)
                .hasMessage("Size cannot be less than 1");
    }

    @Test
    void findByIdReturnSameSize() throws Exception {
        Long id = insertEmployee("address", "name");
        EmployeeEntity employeeEntity = repository.findById(id).orElseGet(EmployeeEntity::new);
        assertThat(employeeEntity.getId()).isEqualTo(BigDecimal.valueOf(id));
    }

    @Test
    void testEmpty() throws Exception {
        EmployeeEntity employeeEntity = repository.findById(1L).orElseGet(EmployeeEntity::new);
        assertThat(employeeEntity.getId()).isNull();
    }

    private Long insertEmployee(String address, String name) throws RepositoryImplementationException {
        EmployeeEntity employee = new EmployeeEntity(null, name, address, LocalDateTime.now());
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
