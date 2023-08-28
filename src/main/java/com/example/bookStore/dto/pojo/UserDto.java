package com.example.bookStore.dto.pojo;

import com.example.bookStore.model.PurchaseOrder;

import java.util.UUID;

public class UserDto {

    private UUID id;

    public UserDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
