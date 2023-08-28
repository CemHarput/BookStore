package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.UserRequestDto;
import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.PurchaseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDtoConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    public UserRequestDto convert(AppUser from) {
        return modelMapper.map(from, UserRequestDto.class);
    }
}
