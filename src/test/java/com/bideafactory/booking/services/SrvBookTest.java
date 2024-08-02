package com.bideafactory.booking.services;

import com.bideafactory.booking.EntityStubs;
import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.dto.DtoOutBook;
import com.bideafactory.booking.exception.BusinessException;
import com.bideafactory.booking.models.Book;
import com.bideafactory.booking.repositories.IBookRepository;
import com.bideafactory.booking.util.ApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SrvBookTest {

    @Mock
    private IBookRepository repository;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private SrvBook srvBook;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateBookWithValidDiscount() throws IOException {
        DtoInBook request = EntityStubs.getInstance().getDtoInBook();
        when(apiClient.validateDiscount(any(DtoInDiscount.class))).thenReturn(true);

        DtoOutBook response = srvBook.createBook(request);

        assertEquals(200, response.getCode());
        assertEquals("Book Accepted", response.getMessage());
        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBookWithInvalidDiscount() throws IOException {
        DtoInBook request = EntityStubs.getInstance().getDtoInBook();
        when(apiClient.validateDiscount(any(DtoInDiscount.class))).thenReturn(false);

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                srvBook.createBook(request)
        );

        assertEquals(400, thrown.getStatusCode());
        assertEquals("Invalid discount", thrown.getMessage());
    }

}