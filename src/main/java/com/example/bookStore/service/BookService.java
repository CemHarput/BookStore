package com.example.bookStore.service;

import com.example.bookStore.dto.converter.BookDtoConverter;
import com.example.bookStore.dto.pojo.BookDto;
import com.example.bookStore.exception.BookNotFoundException;
import com.example.bookStore.model.Book;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.example.bookStore.repository.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final BookDtoConverter bookDtoConverter;

    public BookService(BookRepository bookRepository, BookDtoConverter bookDtoConverter) {
        this.bookRepository = bookRepository;
        this.bookDtoConverter = bookDtoConverter;
    }
    public Page<BookDto> getPaginatedBooksOrderedByCreationDateDesc(int page, int size) {
        Sort sortByCreationDateDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreationDateDesc);

        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookDto> bookDtos = bookPage.getContent().stream()
                .map(bookDtoConverter::convert)
                .collect(Collectors.toList());

        return new PageImpl<>(bookDtos, pageable, bookPage.getTotalElements());
    }

    public void deleteBook(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }

        bookRepository.delete(optionalBook.get());
    }

    public BookDto findBookById(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElse(null);

        return bookDtoConverter.convert(book);

    }
    public BookDto createBook(BookDto bookDto){
        Book book = bookDtoConverter.reverseConvert(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookDtoConverter.convert(savedBook);
    }

    public BookDto updateBook(UUID id, BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }

        Book book = optionalBook.get();

        // Update book properties from bookDto
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setStockQuantity(bookDto.getStockQuantity());
        book.setUpdateAt(new Date());
        book.setPrice(bookDto.getPrice());
        book.setPurchaseOrderFk(bookDto.getPurchaseOrderFk());

        Book updatedBook = bookRepository.save(book);
        return bookDtoConverter.convert(updatedBook);
    }
}
