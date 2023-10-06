package de.workshops.bookshelf.controller;


import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class BookViewController {

    private final BookService bookService;

    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }




    @GetMapping
    public String getAllBooks(Model model){
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("books",allBooks);
        return "books";
    }
}
