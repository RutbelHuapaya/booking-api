package com.bideafactory.booking.mapper;

import com.bideafactory.booking.EntityStubs;
import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IBookMapperTest {

    private IBookMapper bookMapper;
    private DtoInBook dtoInBook;

    @BeforeEach
    void setUp() throws IOException {
        bookMapper = IBookMapper.INSTANCE;
        dtoInBook = EntityStubs.getInstance().getDtoInBook();
    }

    @Test
    void testDtoInBookToBook() {
        Book book = bookMapper.dtoInBookToBook(dtoInBook);

        assertNotNull(book);
        assertEquals(dtoInBook.getId(), book.getId());
        assertEquals(dtoInBook.getName(), book.getName());
        assertEquals(dtoInBook.getLastname(), book.getLastname());
        assertEquals(dtoInBook.getAge(), book.getAge());
        assertEquals(dtoInBook.getPhoneNumber(), book.getPhoneNumber());
        assertEquals(dtoInBook.getStartDate(), book.getStartDate());
        assertEquals(dtoInBook.getEndDate(), book.getEndDate());
        assertEquals(dtoInBook.getHouseId(), book.getHouseId());
        assertEquals(dtoInBook.getDiscountCode(), book.getDiscountCode());
    }

    @Test
    void testDtoInBookToDtoInDiscount() {
        DtoInDiscount dtoInDiscount = bookMapper.dtoInBookToDtoInDiscount(dtoInBook);

        assertNotNull(dtoInDiscount);
        assertEquals(dtoInBook.getId(), dtoInDiscount.getUserId());
        assertEquals(dtoInBook.getHouseId(), dtoInDiscount.getHouseId());
        assertEquals(dtoInBook.getDiscountCode(), dtoInDiscount.getDiscountCode());
    }
}