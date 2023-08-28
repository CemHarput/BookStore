package com.example.bookStore.dto.pojo;

import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.Book;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PurchaseOrderDto {


    private UUID orderId;

    private BigDecimal totalPrice;
    private UserDto user;
    private List<OrderRequestBookDto> books;
    private Date orderDate;

    public PurchaseOrderDto() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<OrderRequestBookDto> getBooks() {
        return books;
    }

    public void setBooks(List<OrderRequestBookDto> books) {
        this.books = books;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
