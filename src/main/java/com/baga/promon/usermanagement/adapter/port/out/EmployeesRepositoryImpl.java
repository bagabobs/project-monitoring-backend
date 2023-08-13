package com.baga.promon.usermanagement.adapter.port.out;


import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEES;
import static com.baga.promon.usermanagement.generated.Sequences.EMPLOYEES_SEQ;
import static java.util.stream.Collectors.toList;

import com.baga.promon.usermanagement.domain.Employee;
import com.baga.promon.usermanagement.generated.tables.records.EmployeesRecord;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
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

   @Override
   public Long delete(Long id) throws RepositoryImplementationException {
      validateDeleteId(id);

      context.delete(EMPLOYEES)
              .where(EMPLOYEES.ID.eq(new BigDecimal(id)))
              .execute();
      return id;
   }

   private void validateDeleteId(Long id) throws RepositoryImplementationException {
      if (id == null) {
         throw new RepositoryImplementationException("ID cannot be empty");
      }
   }

   @Override
   public List<Employee> findAll() throws RepositoryImplementationException {
      List<EmployeesRecord> employeesRecords = context.selectFrom(EMPLOYEES).fetch();
      return employeesRecords.stream()
              .map(v -> new Employee(v.getId(), v.getAddress(), v.getName(), v.getJoinDate()))
              .collect(toList());
   }

   @Override
   public List<Employee> findAfterId(Long id, int size) throws RepositoryImplementationException {
      validateId(id);
      validateSize(size);
      return context.selectFrom(EMPLOYEES)
              .orderBy(EMPLOYEES.ID)
              .seek(BigDecimal.valueOf(id))
              .limit(size)
              .fetchInto(EmployeesRecord.class)
              .stream()
              .map(val -> new Employee(val.getId(), val.getAddress(), val.getName(), val.getJoinDate()))
              .toList();
   }

   private void validateId(Long id) throws RepositoryImplementationException {
      if (id == null) {
         throw new RepositoryImplementationException("ID cannot be empty");
      }
   }

   private void validateSize(int size) throws RepositoryImplementationException {
      if (size < 1) {
         throw new RepositoryImplementationException("Size cannot be less than 1");
      }
   }
}
