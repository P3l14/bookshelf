package de.workshops.bookshelf;

import de.workshops.bookshelf.controller.BookRestController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    private BookRestController bookRestController;


    @Test
    void shouldDeliverListWithBooksOnGetAll() {
        Assertions.assertEquals(3, bookRestController.getAllBooks().size());
    }
}
