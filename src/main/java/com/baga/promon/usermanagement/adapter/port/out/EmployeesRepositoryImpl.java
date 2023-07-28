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
      validateEmployeeForInsert(employee);

      Optional<Record1<BigDecimal>> recordResult = context.insertInto(EMPLOYEES)
              .values(EMPLOYEES_SEQ.nextval(), employee.name(), employee.address(), employee.joinDate())
              .returningResult(EMPLOYEES.ID).fetchOptional();
      return recordResult.orElseThrow(() -> new RepositoryImplementationException("Employee ID is empty"))
              .get(EMPLOYEES.ID).longValue();
   }

   @Override
   public Long update(Employee employee) throws RepositoryImplementationException {
      validateEmployeeForUpdate(employee);

      context.update(EMPLOYEES)
              .set(EMPLOYEES.ADDRESS, employee.address())
              .set(EMPLOYEES.NAME, employee.name())
              .set(EMPLOYEES.JOIN_DATE, employee.joinDate())
              .where(EMPLOYEES.ID.eq(employee.id()))
              .execute();
      return employee.id().longValue();
   }

   private void validateEmployeeForInsert(Employee employee) throws RepositoryImplementationException {
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

   private void validateEmployeeForUpdate(Employee employee) throws RepositoryImplementationException {
      if (employee == null) {
         throw new RepositoryImplementationException("Employee cannot be empty");
      }

      if (employee.id() == null) {
         throw new RepositoryImplementationException("ID in Employee cannot be empty");
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
