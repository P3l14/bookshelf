package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/book")
public class BookRestController {


    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable @Pattern(regexp = "\\d{3}-\\d+")String isbn){
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.createBook(book);
    }

    @GetMapping(params = "author")
    public Book getBookByAuthor(@RequestParam @NotBlank String author){
        return bookService.getBookByAuthor(author);
    }


    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody  @Valid BookSearchRequest bookSearchRequest){
       return bookService.searchBooks(bookSearchRequest.getIsbn(), bookSearchRequest.getAuthor());
    }

//    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
//    public ResponseEntity<String> error(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }


}
