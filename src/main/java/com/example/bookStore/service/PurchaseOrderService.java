package com.example.bookStore.service;

import com.example.bookStore.dto.converter.OrderRequestBookDtoConverter;
import com.example.bookStore.dto.converter.PurchaseOrderDtoConverter;
import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.dto.pojo.OrderRequestBookDto;
import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.PurchaseOrder;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final BookRepository bookRepository;

    private final PurchaseOrderDtoConverter purchaseOrderDtoConverter;

    private final OrderRequestBookDtoConverter orderRequestBookDto;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BookRepository bookRepository, PurchaseOrderDtoConverter purchaseOrderDtoConverter, OrderRequestBookDtoConverter orderRequestBookDto) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.bookRepository = bookRepository;
        this.purchaseOrderDtoConverter = purchaseOrderDtoConverter;
        this.orderRequestBookDto = orderRequestBookDto;
    }

    public PurchaseOrderDto findOrderWithBookDetailsByOrderId(UUID id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderId(id);

        return purchaseOrderDtoConverter.convert(purchaseOrder);
    }

    public PurchaseOrderDto placeOrderWithMinPrice(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderDtoConverter.reverseConvert(purchaseOrderDto);
        updateBooksByISBN(purchaseOrder,purchaseOrderDto);

        BigDecimal totalOrderPrice = calculateTotalOrderPrice(purchaseOrder);

        if (totalOrderPrice.compareTo(new BigDecimal("25")) < 0) {
            throw new IllegalArgumentException("Total order price must be at least $25");
        }
        purchaseOrderDto.setTotalPrice(totalOrderPrice);
        PurchaseOrder savedOrder = purchaseOrderDtoConverter.reverseConvert(purchaseOrderDto);
        savedOrder.setCreatedAt(new Date());
        savedOrder.setBooks(purchaseOrder.getBooks());
        purchaseOrderRepository.save(savedOrder);
        return purchaseOrderDtoConverter.convert(savedOrder);
    }
    private BigDecimal calculateTotalOrderPrice(PurchaseOrder purchaseOrder) {
        BigDecimal total = BigDecimal.ZERO;
        for (Book book : purchaseOrder.getBooks()) {
            total = total.add(book.getPrice());
        }
        return total;
    }

    private void updateBooksByISBN(PurchaseOrder purchaseOrder, PurchaseOrderDto purchaseOrderDto) {
        List<OrderRequestBookDto> listOfBookDtos = new ArrayList<>();
        List<Book> selectedBooks=new ArrayList<>();

        for (OrderRequestBookDto book : purchaseOrderDto.getBooks()) {
            Book existingBook = bookRepository.findByISBN(book.getISBN());

            if (existingBook != null) {
                selectedBooks.add(existingBook);
                OrderRequestBookDto bookDto = orderRequestBookDto.convert(existingBook);
                listOfBookDtos.add(bookDto);
            }
        }
        purchaseOrder.setBooks(selectedBooks);
        purchaseOrderDto.setBooks(listOfBookDtos);
    }

    public List<PurchaseOrderDto> getAllOrderOfAUserByUpdatedDateDesc(UUID userId) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByUser_Id(userId);

        return purchaseOrders.stream().sorted((order1, order2) -> order2.getUpdatedAt().compareTo(order1.getUpdatedAt())).map(purchaseOrderDtoConverter::convert).collect(Collectors.toList());


    }
}
