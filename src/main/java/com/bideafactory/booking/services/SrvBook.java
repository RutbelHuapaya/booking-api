package com.bideafactory.booking.services;

import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.dto.DtoOutBook;
import com.bideafactory.booking.exception.BusinessException;
import com.bideafactory.booking.mapper.IBookMapper;
import com.bideafactory.booking.models.Book;
import com.bideafactory.booking.repositories.IBookRepository;
import com.bideafactory.booking.util.ApiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor()
public class SrvBook implements ISrvBook {

    private static final Logger LOG = LoggerFactory.getLogger(SrvBook.class);

    private final IBookRepository repository;

    private final ApiClient apiClient;

    @Override
    public DtoOutBook createBook(DtoInBook request) {

        LOG.info("... Invoking method SrvBook.createBook ...");

        if (request.getDiscountCode() != null && !request.getDiscountCode().isEmpty()) {
            DtoInDiscount dtoInDiscount = IBookMapper.INSTANCE.dtoInBookToDtoInDiscount(request);
            boolean isValid = apiClient.validateDiscount(dtoInDiscount);

            if (!isValid) {
                throw new BusinessException(400, HttpStatus.CONFLICT, "Invalid discount");
            }
        }

        Book book = IBookMapper.INSTANCE.dtoInBookToBook(request);

        LOG.debug("Saving book: {}", book);
        repository.save(book);

        int code = HttpStatus.OK.value();
        return new DtoOutBook(code, "Book Accepted");
    }

}
