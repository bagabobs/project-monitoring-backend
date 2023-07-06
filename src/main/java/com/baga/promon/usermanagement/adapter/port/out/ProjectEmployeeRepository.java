package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.UserManagementException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;

@Repository
public class ProjectEmployeeRepository {
    private final DSLContext context;

    public ProjectEmployeeRepository(DSLContext context) {
        this.context = context;
    }

    public long save(Employee entity) throws UserManagementException {
        Record1<BigDecimal> record = context.insertInto(EMPLOYEES, EMPLOYEES.ID, EMPLOYEES.ADDRESS,
                        EMPLOYEES.NAME, EMPLOYEES.JOIN_DATE)
                .values(BigDecimal.valueOf(1), entity.address(), entity.name(), entity.joinDate())
                .returningResult(EMPLOYEES.ID)
                .fetchOne();
        if (record == null) {
            throw new UserManagementException("ID is null");
        }
        return record.component1().longValue();
    }
}
