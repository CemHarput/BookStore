package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.OrderRequestBookDto;
import com.example.bookStore.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestBookDtoConverter {

    private final ModelMapper modelMapper = new ModelMapper();
    public OrderRequestBookDto convert(Book from) {

        return modelMapper.map(from, OrderRequestBookDto.class);
    }
    public Book reverseConvert(OrderRequestBookDto from) {
        return modelMapper.map(from, Book.class);
    }
}
