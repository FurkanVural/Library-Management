package com.example.library.controller;
import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("")
public class BookController {

    private final SessionLocaleResolver localeResolver;

    public BookController(SessionLocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }



    @PostMapping("/change-language")
    public String changeLanguage(@RequestParam("lang") String language, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = new Locale(language);
        localeResolver.setLocale(request, response, locale);
        return "redirect:/";
    }

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") BookDto book) {
        Book bookAdd = new Book();
        bookAdd.setBookname(book.getBookname());
        bookAdd.setGenre(book.getGenre());
        bookAdd.setAuthor(book.getAuthor());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(book.getYear(), formatter);
        bookAdd.setYear(date);
        bookRepository.save(bookAdd);
        return "redirect:/list-books";
    }

    @GetMapping("/list-books")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        if(bookRepository.count() != 0) {
            model.addAttribute("books", books);
            return "list_books";
        }
        else {
            return "redirect:/empty";
        }
    }
    @GetMapping("/empty")
    public String emptyPage(){
        return "empty";
    }

    @GetMapping("/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit-book/{id}")
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") BookDto bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
        book.setBookname(bookDetails.getBookname());
        book.setAuthor(bookDetails.getAuthor());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(bookDetails.getYear(), formatter);
        book.setYear(date);
        book.setGenre(bookDetails.getGenre());
        bookRepository.save(book);
        return "redirect:/list-books";
    }

    @PostMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
        bookRepository.deleteById(book.getId());
        return "redirect:/list-books";
    }
}

