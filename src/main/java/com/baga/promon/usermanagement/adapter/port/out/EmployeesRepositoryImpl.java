package com.baga.promon.usermanagement.adapter.port.out;


import static com.baga.promon.usermanagement.generated.Tables.EMPLOYEE_ENTITY;
import static com.baga.promon.usermanagement.generated.Sequences.EMPLOYEE_ENTITY_SEQ;

import com.baga.promon.usermanagement.generated.tables.pojos.EmployeeEntity;
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
   public Long save(EmployeeEntity employee) throws RepositoryImplementationException {
      validateEmployeeForInsert(employee);

      Optional<Record1<BigDecimal>> recordResult = context.insertInto(EMPLOYEE_ENTITY)
              .values(EMPLOYEE_ENTITY_SEQ.nextval(), employee.getName(), employee.getAddress(), employee.getJoinDate())
              .returningResult(EMPLOYEE_ENTITY.ID).fetchOptional();
      return recordResult.orElseThrow(() -> new RepositoryImplementationException("Employee ID is empty"))
              .get(EMPLOYEE_ENTITY.ID).longValue();
   }

   private void validateEmployeeForInsert(EmployeeEntity employee) throws RepositoryImplementationException {
      if (employee == null) {
         throw new RepositoryImplementationException("Employee cannot be empty");
      }

      if (employee.getAddress() == null) {
         throw new RepositoryImplementationException("Address in Employee cannot be empty");
      }

      if (employee.getName() == null) {
         throw new RepositoryImplementationException("Name in Employee cannot be empty");
      }

      if (employee.getJoinDate() == null) {
         throw new RepositoryImplementationException("Join Date in Employee cannot be empty");
      }
   }

   @Override
   public Long update(EmployeeEntity employee) throws RepositoryImplementationException {
      validateEmployeeForUpdate(employee);

      context.update(EMPLOYEE_ENTITY)
              .set(EMPLOYEE_ENTITY.ADDRESS, employee.getAddress())
              .set(EMPLOYEE_ENTITY.NAME, employee.getName())
              .set(EMPLOYEE_ENTITY.JOIN_DATE, employee.getJoinDate())
              .where(EMPLOYEE_ENTITY.ID.eq(employee.getId()))
              .execute();
      return employee.getId().longValue();
   }

   private void validateEmployeeForUpdate(EmployeeEntity employee) throws RepositoryImplementationException {
      if (employee == null) {
         throw new RepositoryImplementationException("Employee cannot be empty");
      }

      if (employee.getId() == null) {
         throw new RepositoryImplementationException("ID in Employee cannot be empty");
      }

      if (employee.getAddress() == null) {
         throw new RepositoryImplementationException("Address in Employee cannot be empty");
      }

      if (employee.getName() == null) {
         throw new RepositoryImplementationException("Name in Employee cannot be empty");
      }

      if (employee.getJoinDate() == null) {
         throw new RepositoryImplementationException("Join Date in Employee cannot be empty");
      }
   }

   @Override
   public Long delete(Long id) throws RepositoryImplementationException {
      validateDeleteId(id);

      context.delete(EMPLOYEE_ENTITY)
              .where(EMPLOYEE_ENTITY.ID.eq(new BigDecimal(id)))
              .execute();
      return id;
   }

   private void validateDeleteId(Long id) throws RepositoryImplementationException {
      if (id == null) {
         throw new RepositoryImplementationException("ID cannot be empty");
      }
   }

   @Override
   public List<EmployeeEntity> findAll() throws RepositoryImplementationException {
      return context.selectFrom(EMPLOYEE_ENTITY).fetchInto(EmployeeEntity.class);
   }

   @Override
   public List<EmployeeEntity> findAfterId(Long id, int size) throws RepositoryImplementationException {
      validateId(id);
      validateSize(size);
      return context.selectFrom(EMPLOYEE_ENTITY)
              .orderBy(EMPLOYEE_ENTITY.ID)
              .seek(BigDecimal.valueOf(id))
              .limit(size)
              .fetchInto(EmployeeEntity.class);
   }

   @Override
   public Optional<EmployeeEntity> findById(Long id) throws RepositoryImplementationException {
      EmployeeEntity employeeEntity = context.selectFrom(EMPLOYEE_ENTITY).where(EMPLOYEE_ENTITY.ID.eq(BigDecimal.valueOf(id)))
              .fetchOneInto(EmployeeEntity.class);
      return Optional.ofNullable(employeeEntity);
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
