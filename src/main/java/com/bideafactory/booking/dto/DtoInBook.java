package com.bideafactory.booking.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInBook {

    @NotNull
    @Size(min = 9, max = 10)
    private String id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastname;

    @NotNull
    @Min(18)
    @Max(100)
    private int age;

    @NotNull
    @Size(min = 9, max = 20)
    private String phoneNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(min = 6, max = 15)
    private String houseId;

    @Size(min = 8, max = 8)
    private String discountCode;

}
