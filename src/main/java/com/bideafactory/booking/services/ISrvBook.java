package com.bideafactory.booking.services;

import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoOutBook;

public interface ISrvBook {

    DtoOutBook createBook(DtoInBook book);
}
