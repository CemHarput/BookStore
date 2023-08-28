package com.example.bookStore.service;

import com.example.bookStore.dto.converter.OrderRequestBookDtoConverter;
import com.example.bookStore.dto.converter.PurchaseOrderDtoConverter;
import com.example.bookStore.dto.converter.PurchaseRequestDtoConverter;
import com.example.bookStore.dto.pojo.OrderRequestBookDto;
import com.example.bookStore.dto.pojo.PurchaseOrderDto;
import com.example.bookStore.dto.pojo.PurchaseRequestDto;
import com.example.bookStore.exception.UserCannotBeFoundException;
import com.example.bookStore.model.AppUser;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.PurchaseOrder;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.PurchaseOrderRepository;
import com.example.bookStore.repository.UserRepository;
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

    private final PurchaseRequestDtoConverter purchaseRequestDtoConverter;

    private final UserRepository userRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BookRepository bookRepository, PurchaseOrderDtoConverter purchaseOrderDtoConverter, OrderRequestBookDtoConverter orderRequestBookDto, PurchaseRequestDtoConverter purchaseRequestDtoConverter, UserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.bookRepository = bookRepository;
        this.purchaseOrderDtoConverter = purchaseOrderDtoConverter;
        this.orderRequestBookDto = orderRequestBookDto;
        this.purchaseRequestDtoConverter = purchaseRequestDtoConverter;
        this.userRepository = userRepository;
    }

    public PurchaseRequestDto findOrderWithBookDetailsByOrderId(UUID id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderId(id);
        return purchaseRequestDtoConverter.convert(purchaseOrder);
    }

    public PurchaseOrderDto placeOrderWithMinPrice(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = createPurchaseOrderFromDto(purchaseOrderDto);
        updateBooksByISBN(purchaseOrder, purchaseOrderDto);
        validateMinOrderPrice(purchaseOrder);

        BigDecimal totalOrderPrice = calculateTotalOrderPrice(purchaseOrder);
        purchaseOrderDto.setTotalPrice(totalOrderPrice);

        savePurchaseOrder(purchaseOrderDto, purchaseOrder);

        return purchaseOrderDtoConverter.convert(purchaseOrder);
    }

    private PurchaseOrder createPurchaseOrderFromDto(PurchaseOrderDto purchaseOrderDto) {
        return purchaseOrderDtoConverter.reverseConvert(purchaseOrderDto);
    }

    private void updateBooksByISBN(PurchaseOrder purchaseOrder, PurchaseOrderDto purchaseOrderDto) {
        List<Book> selectedBooks = new ArrayList<>();

        for (OrderRequestBookDto bookDto : purchaseOrderDto.getBooks()) {
            Book existingBook = bookRepository.findByISBN(bookDto.getISBN());

            if (existingBook != null) {
                selectedBooks.add(existingBook);
            }
        }
        purchaseOrder.setBooks(selectedBooks);
        purchaseOrderDto.setBooks(convertBooksToDto(selectedBooks));
    }

    private void validateMinOrderPrice(PurchaseOrder purchaseOrder) {
        BigDecimal totalOrderPrice = calculateTotalOrderPrice(purchaseOrder);

        if (totalOrderPrice.compareTo(new BigDecimal("25")) < 0) {
            throw new IllegalArgumentException("Total order price must be at least $25");
        }
    }

    private List<OrderRequestBookDto> convertBooksToDto(List<Book> books) {
        return books.stream()
                .map(orderRequestBookDto::convert)
                .collect(Collectors.toList());
    }

    private void savePurchaseOrder(PurchaseOrderDto purchaseOrderDto, PurchaseOrder purchaseOrder) {
        PurchaseOrder savedOrder = purchaseOrderDtoConverter.reverseConvert(purchaseOrderDto);
        savedOrder.setCreatedAt(new Date());
        savedOrder.setBooks(purchaseOrder.getBooks());
        purchaseOrderRepository.save(savedOrder);
    }

    private BigDecimal calculateTotalOrderPrice(PurchaseOrder purchaseOrder) {
        BigDecimal total = BigDecimal.ZERO;
        for (Book book : purchaseOrder.getBooks()) {
            total = total.add(book.getPrice());
        }
        return total;
    }


    public List<PurchaseOrderDto> getAllOrderOfAUserByUpdatedDateDesc(UUID userId) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByUser_Id(userId);

        return purchaseOrders.stream().sorted((order1, order2) -> {
            Date updatedAt1 = order1.getUpdatedAt() != null ? order1.getUpdatedAt() : new Date();
            Date updatedAt2 = order2.getUpdatedAt() != null ? order2.getUpdatedAt() : new Date();
            return updatedAt2.compareTo(updatedAt1);
        }).map(purchaseOrderDtoConverter::convert).collect(Collectors.toList());


    }
}
