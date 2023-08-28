package com.example.bookStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This is the foreign key column in the Order table
    private AppUser user;
    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
    })
    private List<Book> books;


    private Date orderDate;
    private Date createdAt;
    private Date updatedAt;

    public PurchaseOrder() {
    }
    public PurchaseOrder(UUID orderId, BigDecimal totalPrice, AppUser user, List<Book> books, Date orderDate, Date createdAt, Date updatedAt) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.user = user;
        this.books = books;
        this.orderDate = orderDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseOrder purchaseOrder)) return false;
        return Objects.equals(getOrderId(), purchaseOrder.getOrderId()) && Objects.equals(getTotalPrice(), purchaseOrder.getTotalPrice()) && Objects.equals(getUser(), purchaseOrder.getUser()) && Objects.equals(getBooks(), purchaseOrder.getBooks()) && Objects.equals(getOrderDate(), purchaseOrder.getOrderDate()) && Objects.equals(getCreatedAt(), purchaseOrder.getCreatedAt()) && Objects.equals(getUpdatedAt(), purchaseOrder.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getTotalPrice(), getUser(), getBooks(), getOrderDate(), getCreatedAt(), getUpdatedAt());
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
