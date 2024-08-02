package com.bideafactory.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoOutDiscount {
    private String houseId;
    private String discountCode;
    private String id;
    private String userId;
    private boolean status;
}
