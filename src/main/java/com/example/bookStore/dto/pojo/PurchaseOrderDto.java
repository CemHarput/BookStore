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
    private AppUser user;
    private List<BookDto> books;
    private Date orderDate;
    private Date createdAt;
    private Date updatedAt;

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

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
