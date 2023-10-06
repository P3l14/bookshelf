package de.workshops.bookshelf.service;

import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.repository.BookRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


    public Book getBookByIsbn(String isbn){
        return bookRepository.findAll().stream()
                .filter(b -> Objects.equals(isbn, b.getIsbn()))
                .findFirst().orElse(null);
    }

    @GetMapping(params = "author")
    public Book getBookByAuthor(@RequestParam @NotBlank String author){
        return bookRepository.findAll().stream()
                .filter(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().contains(author))
                .findFirst().orElse(null);
    }


    public List<Book> searchBooks(String isbn, String author){
        return bookRepository.findAll().stream()
                .filter(b -> Objects.equals(isbn, b.getIsbn()))
                .filter(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().contains(author))
                .collect(Collectors.toList());
    }


    public Book createBook(Book book) {
        return bookRepository.createBook(book);
    }
}
