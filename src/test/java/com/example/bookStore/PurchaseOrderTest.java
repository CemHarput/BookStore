package com.example.bookStore;

import com.example.bookStore.dto.converter.OrderRequestBookDtoConverter;
import com.example.bookStore.dto.converter.PurchaseOrderDtoConverter;
import com.example.bookStore.dto.converter.PurchaseRequestDtoConverter;
import com.example.bookStore.dto.pojo.PurchaseRequestDto;
import com.example.bookStore.model.PurchaseOrder;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.PurchaseOrderRepository;
import com.example.bookStore.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PurchaseOrderTest {
    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PurchaseOrderDtoConverter purchaseOrderDtoConverter;

    @Mock
    private OrderRequestBookDtoConverter orderRequestBookDtoConverter;

    @Mock
    private PurchaseRequestDtoConverter purchaseRequestDtoConverter;

    private PurchaseOrderService purchaseOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseOrderService = new PurchaseOrderService(
                purchaseOrderRepository,
                bookRepository,
                purchaseOrderDtoConverter,
                orderRequestBookDtoConverter,
                purchaseRequestDtoConverter
        );
    }

    @Test
    void testFindOrderWithBookDetailsByOrderId_OrderFound() {
        UUID orderId = UUID.randomUUID();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        when(purchaseOrderRepository.findByOrderId(orderId)).thenReturn(purchaseOrder);

        // Mock the conversion process
        PurchaseRequestDto expectedDto = new PurchaseRequestDto();
        when(purchaseRequestDtoConverter.convert(purchaseOrder)).thenReturn(expectedDto);

        PurchaseRequestDto resultDto = purchaseOrderService.findOrderWithBookDetailsByOrderId(orderId);

        assertEquals(expectedDto, resultDto);
    }
}
