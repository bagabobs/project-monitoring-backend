package com.baga.promon.usermanagement.adapter.port.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import com.baga.promon.usermanagement.application.port.in.LoadEmployeeUseCase;
import com.baga.promon.usermanagement.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(controllers = LoadEmployeeController.class)
public class LoadEmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoadEmployeeUseCase loadEmployeeUseCase;

    @Test
    void testLoadEmployee() throws Exception {
        List<Employee> employees = List.of(
                new Employee(BigDecimal.valueOf(1L), "address", "name", LocalDateTime.now()),
                new Employee(BigDecimal.valueOf(2L), "address2", "name", LocalDateTime.now()));
        when(loadEmployeeUseCase.loadAllEmployee()).thenReturn(employees);
        mockMvc.perform(get("/v1/employee/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", Matchers.containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].address", Matchers.containsInAnyOrder("address", "address2")));
    }
}
