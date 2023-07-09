package com.baga.promon.usermanagement.adapter.port.out;


import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static com.baga.promon.usermanagement.generated.Sequences.EMPLOYEES_SEQ;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.PersistenceRepositoryException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class EmployeesRepositoryImpl implements EmployeesRepository<Long, Employee> {
   private final DSLContext context;

   public EmployeesRepositoryImpl(DSLContext context) {
       this.context = context;
   }

   @Override
   public Long save(Employee employee) throws PersistenceRepositoryException {
      Optional<Record1<BigDecimal>> recordResult = context.insertInto(EMPLOYEES)
              .values(EMPLOYEES_SEQ.nextval(), employee.name(), employee.address(), employee.joinDate())
              .returningResult(EMPLOYEES.ID).fetchOptional();
      return recordResult.orElseThrow(() -> new PersistenceRepositoryException("Employee ID is empty"))
              .get(EMPLOYEES.ID).longValue();
   }
}
