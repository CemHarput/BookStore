package com.example.bookStore.dto.pojo;



import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class BookDto {
    private UUID ISBN;
    private String title;
    private String author;
    private BigDecimal price;
    private int stockQuantity;
    private Date createdAt;
    private Date updateAt;
    private String order;

    private UUID purchaseOrderFk;
    public BookDto() {
    }

    public UUID getISBN() {
        return ISBN;
    }

    public void setISBN(UUID ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public UUID getPurchaseOrderFk() {
        return purchaseOrderFk;
    }

    public void setPurchaseOrderFk(UUID purchaseOrderFk) {
        this.purchaseOrderFk = purchaseOrderFk;
    }
}
