package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookDtoConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public BookDto convert(Book from) {
        return modelMapper.map(from, BookDto.class);
    }

    public Book reverseConvert(BookDto from) {
        return modelMapper.map(from, Book.class);
    }
}
