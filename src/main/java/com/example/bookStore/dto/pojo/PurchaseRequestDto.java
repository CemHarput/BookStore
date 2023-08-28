package com.example.bookStore.dto.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDto {
    private UUID orderId;
    private BigDecimal totalPrice;
    private UserRequestDto user;
    private List<BookDto> books;
    private Date orderDate;
}
