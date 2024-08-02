package com.bideafactory.booking.mapper;

import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.models.Book;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper
public interface IBookMapper {

    IBookMapper INSTANCE = Mappers.getMapper(IBookMapper.class);

    Logger LOG = LoggerFactory.getLogger(IBookMapper.class);

    Book dtoInBookToBook(DtoInBook dtoInBook);

    @Mapping(source = "id", target = "userId")
    DtoInDiscount dtoInBookToDtoInDiscount(DtoInBook dtoInBook);

    @BeforeMapping
    default void logMapIn(DtoInBook dtoInBook) {
        LOG.info("... invoking service IBookMapper ...");
    }
}
