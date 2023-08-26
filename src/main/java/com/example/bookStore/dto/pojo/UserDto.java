package com.example.bookStore.dto.pojo;

import com.example.bookStore.model.PurchaseOrder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;
    private Date updateAt;
    private List<PurchaseOrderDto> purchaseOrders;

    public UserDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public List<PurchaseOrderDto> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrderDto> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
