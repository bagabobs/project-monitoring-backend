package com.baga.promon.usermanagement.adapter.port.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = LoadEmployeeController.class)
class LoadEmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoadEmployeeUseCase loadEmployeeUseCase;
    private List<Employee> employees;

    @BeforeEach
    public void init() {
        createEmployees();
    }

    @Test
    void testGetAllEmployee() throws Exception {
        when(loadEmployeeUseCase.loadAllEmployee()).thenReturn(employees.subList(0, 2));
        mockMvc.perform(get("/v1/employee/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].address", Matchers.containsInAnyOrder("address1", "address2")));
    }

    @Test
    void testGetEmployeeByPage() throws Exception {
        when(loadEmployeeUseCase.loadEmployeeByPage((long) 1, 1)).thenReturn(employees.subList(0, 1));
        mockMvc.perform(get("/v1/employee/getbypage").param("page", "1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].address", Matchers.containsInAnyOrder("address1")));
    }

    @Test
    void returnBadRequestWhenPageParamEmpty() throws Exception {
        mockMvc.perform(get("/v1/employee/getbypage").param("page", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnBadRequestWhenSizeParamEmpty() throws Exception {
        mockMvc.perform(get("/v1/employee/getbypage").param("size", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getFirstFourEmployeeWhenPageIs1AndSizeIs4() throws Exception {
        when(loadEmployeeUseCase.loadEmployeeByPage((long) 1, 4)).thenReturn(employees.subList(0, 4));
        mockMvc.perform(get("/v1/employee/getbypage").param("page", "1")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2, 3, 4)))
                .andExpect(jsonPath("$[*].address", Matchers.containsInAnyOrder("address1", "address2",
                        "address3", "address4")));
    }

    @Test
    void getEmployeeByIdReturnEmployee() throws Exception {
        when(loadEmployeeUseCase.loadEmployeeById(1L)).thenReturn(Optional.of(employees.get(0)));
        mockMvc.perform(get("/v1/employee/getbyid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id", Matchers.contains(1)));
    }

    @Test
    void getEmployeeByIdWhenEmployeeNotFoundReturnStatusOkAndEmptyString() throws Exception {
        when(loadEmployeeUseCase.loadEmployeeById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/employee/getbyid/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    private void createEmployees() {
        employees = List.of(
                new Employee(BigDecimal.valueOf(1L), "address1", "name1", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(2L), "address2", "name2", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(3L), "address3", "name3", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(4L), "address4", "name4", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(5L), "address5", "name5", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(6L), "address6", "name6", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(7L), "address7", "name7", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(8L), "address8", "name8", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(9L), "address9", "name9", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(10L), "address10", "name10", LocalDateTime.now())
        );
    }
}
