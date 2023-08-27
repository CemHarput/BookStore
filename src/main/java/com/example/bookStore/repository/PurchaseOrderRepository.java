package com.example.bookStore.repository;


import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    List<PurchaseOrder> findByUser_Id(UUID userId);
}
