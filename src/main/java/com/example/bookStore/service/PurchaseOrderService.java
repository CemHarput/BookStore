package com.example.bookStore.service;

import com.example.bookStore.dto.converter.PurchaseOrderDtoConverter;
import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.PurchaseOrder;
import com.example.bookStore.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderDtoConverter purchaseOrderDtoConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderDtoConverter purchaseOrderDtoConverter, EntityManager entityManager) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderDtoConverter = purchaseOrderDtoConverter;
        this.entityManager = entityManager;
    }

    public List<PurchaseOrderDto> getAllOrderOrderedByUpdateDateDesc() {
        Sort sortByUpdateDateDesc = Sort.by(Sort.Direction.DESC, "updatedAt");

        return purchaseOrderRepository.findAll(sortByUpdateDateDesc)
                .stream()
                .map(purchaseOrderDtoConverter::convert)
                .collect(Collectors.toList());
    }
    public PurchaseOrderDto findOrderWithBookDetailsByOrderId(UUID id) {
        PurchaseOrder purchaseOrder = entityManager.createQuery(
                        "SELECT po FROM PurchaseOrder po " +
                                " INNER JOIN FETCH po.books " +
                                "WHERE po.orderId = :id", PurchaseOrder.class)
                .setParameter("id", id)
                .getSingleResult();

        return purchaseOrderDtoConverter.convert(purchaseOrder);
    }

    public PurchaseOrderDto placeOrderWithMinPrice(PurchaseOrderDto purchaseOrderDto) {
        BigDecimal totalOrderPrice = calculateTotalOrderPrice(purchaseOrderDto.getBooks());

        if (totalOrderPrice.compareTo(new BigDecimal("25")) < 0) {
            throw new IllegalArgumentException("Total order price must be at least $25");
        }
        purchaseOrderDto.setTotalPrice(totalOrderPrice);

        PurchaseOrder purchaseOrder = purchaseOrderDtoConverter.reverseConvert(purchaseOrderDto);
        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrderDtoConverter.convert(savedOrder);
    }
    private BigDecimal calculateTotalOrderPrice(List<BookDto> books) {
        BigDecimal total = BigDecimal.ZERO;
        for (BookDto book : books) {
            total = total.add(book.getPrice());
        }
        return total;
    }

    public List<PurchaseOrderDto> getAllOrderOfAUserByUpdatedDateDesc(AppUser user) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByUser(user);

        return purchaseOrders.stream().sorted((order1, order2) -> order2.getUpdatedAt().compareTo(order1.getUpdatedAt())).map(purchaseOrderDtoConverter::convert).collect(Collectors.toList());


    }
}
