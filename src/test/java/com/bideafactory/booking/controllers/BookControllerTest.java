package com.bideafactory.booking.controllers;

import com.bideafactory.booking.EntityStubs;
import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoOutBook;
import com.bideafactory.booking.services.ISrvBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private ISrvBook srvBook;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private DtoInBook dtoInBook;
    private DtoOutBook dtoOutBook;

    @BeforeEach
    void setUp() throws IOException {

        dtoInBook = EntityStubs.getInstance().getDtoInBook();
        dtoOutBook = new DtoOutBook(200, "Book Accepted");
    }

    @Test
    void testcreateBook() throws Exception {
        when(srvBook.createBook(any(DtoInBook.class))).thenReturn(dtoOutBook);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bideafactory/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book Accepted"))
                .andReturn();

        assertNotNull(result.getResponse());
        verify(srvBook, times(1)).createBook(any(DtoInBook.class));
    }


}