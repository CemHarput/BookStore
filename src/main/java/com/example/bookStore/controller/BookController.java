package com.example.bookStore.controller;

import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.exception.BookGetAllFailedException;
import com.example.bookStore.exception.BookNotFoundException;
import com.example.bookStore.service.RateLimitingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.bookStore.service.BookService;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class BookController {

    private static final Logger logger =  LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final RateLimitingService rateLimitingService;

    public BookController(BookService bookService, RateLimitingService rateLimitingService) {
        this.bookService = bookService;
        this.rateLimitingService = rateLimitingService;
    }

    @Operation(tags = "Book Controller")
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {

        try {
            return ResponseEntity.ok(bookService.getAllBooksOrderedByCreationDateDesc());
        } catch (Exception e) {
            logger.error("Failed to get all books",e);
            throw new BookGetAllFailedException("Failed to get all books ");
        }

    }

    @Operation(tags = "Book Controller")
    @DeleteMapping("/books/{isbn}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable UUID isbn) {
        try {
            bookService.deleteBook(isbn);
            if(rateLimitingService.tryAcquire()){
                return ResponseEntity.ok("Book deleted successfully");
            }else{
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
            }
        } catch (BookNotFoundException e) {
            logger.error("Failed to delete the book",e);
            throw new BookNotFoundException("Book not found");
        }
    }

    @Operation(tags = "Book Controller")
    @PostMapping("/addBooks")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        try {
            BookDto createdBook = bookService.createBook(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (Exception e) {
            logger.error("Failed to create to book",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(tags = "Book Controller")
    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> findById(@PathVariable UUID isbn) {


        try {
            BookDto bookDto = bookService.findBookById(isbn);

            return ResponseEntity.ok(bookDto);
        } catch (Exception e) {
            logger.error("Failed to get the book", e);
            throw new BookNotFoundException("Book not found");
        }

    }

    @Operation(tags = "Book Controller")
    @PutMapping("/books/{isbn}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDto> updateBook(@PathVariable UUID isbn, @RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = bookService.updateBook(isbn, bookDto);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException e) {
            logger.error("Failed to update the book",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
