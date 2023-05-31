package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public Book saveBook(Book book) { return bookRepository.save(book); }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
