package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.controller.BookRestController;
import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.service.BookService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookMockMvcMockedBareTest {

    @Mock
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    BookRestController bookRestController;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldDeliverListWithBooksOnGetAll() throws Exception {

        Book b1 = new Book();
        b1.setTitle("Design");
        b1.setAuthor("Erich");
        Book b2 = new Book();
        b2.setTitle("Clean");
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(b1,b2));
        ReflectionTestUtils.setField(bookRestController,"bookService", bookService);

        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.equalTo("Design")))
                .andExpect(jsonPath("$[0].author", Matchers.equalTo("Erich")))
                .andExpect(jsonPath("$[1].title", Matchers.equalTo("Clean")));

    }

    @Test
    void shouldDeliverListWithBooksOnGetAll2() throws Exception {
        Book b1 = new Book();
        b1.setTitle("Patterns");
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(b1));
        ReflectionTestUtils.setField(bookRestController,"bookService", bookService);

        MvcResult mvcResult = mockMvc.perform(get("/book")).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(json, new TypeReference<>() {});
        MatcherAssert.assertThat(books,hasSize(1));
        MatcherAssert.assertThat(books.get(0).getTitle(),equalTo("Patterns"));
    }
}
