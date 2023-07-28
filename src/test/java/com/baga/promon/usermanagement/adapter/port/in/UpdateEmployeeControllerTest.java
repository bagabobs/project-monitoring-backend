package com.baga.promon.usermanagement.adapter.port.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baga.promon.usermanagement.application.port.in.UpdateEmployeeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

@WebMvcTest(controllers = UpdateEmployeeController.class)
public class UpdateEmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    @Test
    void testUpdate() throws Exception {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(1L, "name", "address", new Date());
        mockMvc.perform(put("/v1/employee/update")
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
