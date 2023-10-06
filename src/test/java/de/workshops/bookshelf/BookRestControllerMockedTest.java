package de.workshops.bookshelf;

import de.workshops.bookshelf.controller.BookRestController;
import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class BookRestControllerMockedTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private BookRestController bookRestController;


    @Test
    void shouldDeliverListWithBooksOnGetAll() {
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(new Book(), new Book()));
        Assertions.assertEquals(2, bookRestController.getAllBooks().size());
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(new Book()));
        Assertions.assertEquals(1, bookRestController.getAllBooks().size());
        Assertions.assertEquals(1, bookRestController.getAllBooks().size());
    }
}
