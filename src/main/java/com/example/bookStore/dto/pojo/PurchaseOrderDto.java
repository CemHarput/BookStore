package com.example.bookStore.dto.pojo;

import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.Book;
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
public class PurchaseOrderDto {

    private BigDecimal totalPrice;
    private UserDto user;
    private List<OrderRequestBookDto> books;
    private Date orderDate;

}
