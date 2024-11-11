package com.mindfire.parkinglot.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GlobalExceptionHandlerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Initialize MockMvc with the web application context
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHandleSlotNotFoundException() throws Exception {
        String path = "/api/v1/slot/999";  // Simulating a non-existent slot ID

        mockMvc.perform(get(path))
                .andExpect(status().isNotFound())  // Expecting 404 status code
                .andExpect(jsonPath("$.status").value(404))  // Assert that the "status" is 404
                .andExpect(jsonPath("$.error").value("Not Found"))  // Assert the "error" is "Not Found"
                .andExpect(jsonPath("$.message").value("Slot not found for the given ID - 999"))  // Assert the exception message
                .andExpect(jsonPath("$.path").value(path))  // Assert that the path is correct
                .andExpect(jsonPath("$.timestamp").exists())  // Ensure timestamp is present
                .andExpect(jsonPath("$.timestamp").value(org.hamcrest.Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")));  // Validate timestamp format
    }

    @Test
    public void testHandleGenericException() throws Exception {
        String path = "/api/v1/unknown-error";  // Simulate a generic error path

        mockMvc.perform(get(path))
                .andExpect(status().isNotFound());
    }
}