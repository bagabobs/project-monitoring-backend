package com.baga.promon.usermanagement.adapter.port.in;

import com.baga.promon.usermanagement.application.port.in.DeleteEmployeeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeleteEmployeeController.class)
public class DeleteEmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeleteEmployeeUseCase deleteEmployeeUseCase;

    @Test
    void testDelete() throws Exception {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(1L);
        mockMvc.perform(delete("/v1/employee/save")
                .content(asJsonString(command))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
