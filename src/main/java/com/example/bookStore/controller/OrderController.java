package com.example.bookStore.controller;


import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.PurchaseOrder;
import com.example.bookStore.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    private static final Logger logger =  LoggerFactory.getLogger(BookController.class);
    private final PurchaseOrderService orderService;
    public OrderController(PurchaseOrderService orderService) {
        this.orderService = orderService;
    }
    @Operation(tags = "Order Controller")
    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderDto> placeAOrder(@RequestBody PurchaseOrderDto requestedOrderDto) {
        try {
            PurchaseOrderDto purchasedOrderDto = orderService.placeOrderWithMinPrice(requestedOrderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(purchasedOrderDto);
        } catch (Exception e) {
            logger.error("Failed to place an order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Operation(tags = "Order Controller")
    @PostMapping("/orders/{userId}")
    public ResponseEntity<List<PurchaseOrderDto>> getOrderOfAUser(@PathVariable AppUser userId) {
        try {
            List<PurchaseOrderDto> purchasedOrdersDto = orderService.getAllOrderOfAUserByUpdatedDateDesc(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(purchasedOrdersDto);
        } catch (Exception e) {
            logger.error("Failed to get orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Operation(tags = "Order Controller")
    @PostMapping("/orders/details/{orderId}")
    public ResponseEntity<PurchaseOrderDto> getOrderOfAUser(@PathVariable UUID orderId) {
        try {
            PurchaseOrderDto purchasedOrderDtoWithBookDetails = orderService.findOrderWithBookDetailsByOrderId(orderId);
            return ResponseEntity.status(HttpStatus.CREATED).body(purchasedOrderDtoWithBookDetails);
        } catch (Exception e) {
            logger.error("Failed to get order's details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
