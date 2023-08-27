package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findByISBN(UUID isbn);
}
