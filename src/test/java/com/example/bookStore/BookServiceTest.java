package com.example.bookStore;

import com.example.bookStore.dto.converter.BookDtoConverter;
import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.exception.BookNotFoundException;
import com.example.bookStore.model.Book;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookDtoConverter bookDtoConverter;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookDtoConverter);
    }
    @Test
    void testDeleteBook_BookNotFound() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(bookId));

        verify(bookRepository, never()).delete(any(Book.class));
    }
    @Test
    public void testFindBookById_BookExists() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setTitle("Sample Title");
        book.setAuthor("Sample Author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDto expectedDto = new BookDto();
        expectedDto.setTitle("Sample Title");
        expectedDto.setAuthor("Sample Author");
        when(bookDtoConverter.convert(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.findBookById(bookId);

        assertEquals(expectedDto.getTitle(), actualDto.getTitle());
        assertEquals(expectedDto.getAuthor(), actualDto.getAuthor());
    }
    @Test
    public void testCreateBook() {
        BookDto inputDto = new BookDto();
        inputDto.setTitle("Sample Title");
        inputDto.setAuthor("Sample Author");
        inputDto.setPrice(BigDecimal.valueOf(29.99));
        inputDto.setStockQuantity(100);

        Book bookToSave = new Book();
        when(bookDtoConverter.reverseConvert(inputDto)).thenReturn(bookToSave);

        Book savedBook = new Book();
        savedBook.setTitle("Sample Title");
        savedBook.setAuthor("Sample Author");
        savedBook.setPrice(BigDecimal.valueOf(29.99));
        savedBook.setStockQuantity(100);
        savedBook.setCreatedAt(new Date());
        when(bookRepository.save(bookToSave)).thenReturn(savedBook);

        BookDto expectedDto = new BookDto();
        expectedDto.setTitle("Sample Title");
        expectedDto.setAuthor("Sample Author");
        expectedDto.setPrice(BigDecimal.valueOf(29.99));
        expectedDto.setStockQuantity(100);
        when(bookDtoConverter.convert(savedBook)).thenReturn(expectedDto);

        BookDto actualDto = bookService.createBook(inputDto);

        assertEquals(expectedDto.getTitle(), actualDto.getTitle());
        assertEquals(expectedDto.getAuthor(), actualDto.getAuthor());
        assertEquals(expectedDto.getPrice(), actualDto.getPrice());
        assertEquals(expectedDto.getStockQuantity(), actualDto.getStockQuantity());
    }
}
