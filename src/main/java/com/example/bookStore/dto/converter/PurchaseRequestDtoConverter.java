package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.dto.pojo.PurchaseRequestDto;
import com.example.bookStore.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseRequestDtoConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    public PurchaseRequestDto convert(PurchaseOrder from) {
        return modelMapper.map(from, PurchaseRequestDto.class);
    }
}
