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
    void testGetAllBooksOrderedByCreationDateDesc() {
        Book firstBook= Book.builder().ISBN(UUID.randomUUID()).title("Book 1").author("Author 1").price(BigDecimal.valueOf(19.99)).stockQuantity(10).createdAt(new Date()).build();
        Book secondBook= Book.builder().ISBN(UUID.randomUUID()).title("Book 2").author("Author 2").price(BigDecimal.valueOf(29.99)).stockQuantity(5).createdAt(new Date()).build();

        List<Book> mockBooks = Arrays.asList(firstBook, secondBook);

        when(bookRepository.findAll(any(Sort.class))).thenReturn(mockBooks);
        when(bookDtoConverter.convert(any(Book.class))).thenReturn(new BookDto());

        List<BookDto> result = bookService.getAllBooksOrderedByCreationDateDesc();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll(any(Sort.class));
        verify(bookDtoConverter, times(2)).convert(any(Book.class));
    }
    @Test
    void testDeleteBook() {
        UUID bookId = UUID.randomUUID();
        Book book= Book.builder().ISBN(UUID.randomUUID()).title("Book").author("Author").price(BigDecimal.valueOf(19.99)).stockQuantity(10).createdAt(new Date()).build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> bookService.deleteBook(bookId));

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_BookNotFound() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(bookId));

        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void testFindBookById() {
        UUID bookId = UUID.randomUUID();
        Book book= Book.builder().ISBN(UUID.randomUUID()).title("Book").author("Author").price(BigDecimal.valueOf(19.99)).stockQuantity(10).createdAt(new Date()).build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookDtoConverter.convert(book)).thenReturn(new BookDto());

        BookDto result = bookService.findBookById(bookId);

        assertNotNull(result);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookDtoConverter, times(1)).convert(book);
    }
}
