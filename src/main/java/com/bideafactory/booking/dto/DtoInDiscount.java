package com.bideafactory.booking.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInDiscount {
    private String userId;
    private String houseId;
    private String discountCode;
}
