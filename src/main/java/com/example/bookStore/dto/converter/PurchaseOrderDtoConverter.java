package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderDtoConverter {

    private final ModelMapper modelMapper = new ModelMapper();
    public PurchaseOrderDto convert(PurchaseOrder from) {
        return modelMapper.map(from, PurchaseOrderDto.class);
    }
    public PurchaseOrder reverseConvert(PurchaseOrderDto from) {
        return modelMapper.map(from, PurchaseOrder.class);
    }
}
