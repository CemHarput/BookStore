package com.example.bookStore.dto.converter;

import com.example.bookStore.dto.pojo.UserDto;
import com.example.bookStore.model.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convert(AppUser from) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(from, UserDto.class);
    }
}
