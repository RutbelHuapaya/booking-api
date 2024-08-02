package com.bideafactory.booking.controllers;

import com.bideafactory.booking.dto.DtoInBook;
import com.bideafactory.booking.dto.DtoOutBook;
import com.bideafactory.booking.services.ISrvBook;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bideafactory")
@RequiredArgsConstructor
public class BookController {

    private final ISrvBook srvBook;

    @PostMapping(value = "/book", headers = "Accept=application/json")
    public ResponseEntity<DtoOutBook> createBook(@RequestBody @Valid DtoInBook dto) {
        return ResponseEntity.ok(srvBook.createBook(dto));
    }


}
