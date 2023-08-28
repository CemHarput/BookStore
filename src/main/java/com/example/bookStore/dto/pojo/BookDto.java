package com.example.bookStore.dto.pojo;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private UUID ISBN;
    private String title;
    private String author;
    private BigDecimal price;
    private int stockQuantity;


}
