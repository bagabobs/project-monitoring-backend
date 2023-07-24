package com.baga.promon.usermanagement.adapter.port.out;


import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static com.baga.promon.usermanagement.generated.Sequences.EMPLOYEES_SEQ;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class EmployeesRepositoryImpl implements EmployeesRepository {
   private final DSLContext context;

   public EmployeesRepositoryImpl(DSLContext context) {
       this.context = context;
   }

   @Override
   public Long save(Employee employee) throws RepositoryImplementationException {
      validateEmployee(employee);

      Optional<Record1<BigDecimal>> recordResult = context.insertInto(EMPLOYEES)
              .values(EMPLOYEES_SEQ.nextval(), employee.name(), employee.address(), employee.joinDate())
              .returningResult(EMPLOYEES.ID).fetchOptional();
      return recordResult.orElseThrow(() -> new RepositoryImplementationException("Employee ID is empty"))
              .get(EMPLOYEES.ID).longValue();
   }

   private void validateEmployee(Employee employee) throws RepositoryImplementationException {
      if (employee == null) {
         throw new RepositoryImplementationException("Employee cannot be empty");
      }

      if (employee.address() == null) {
         throw new RepositoryImplementationException("Address in Employee cannot be empty");
      }

      if (employee.name() == null) {
         throw new RepositoryImplementationException("Name in Employee cannot be empty");
      }

      if (employee.joinDate() == null) {
         throw new RepositoryImplementationException("Join Date in Employee cannot be empty");
      }
   }
}
