package de.workshops.bookshelf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.workshops.bookshelf.model.Book;
import de.workshops.bookshelf.service.BookService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchersDsl;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
class BookMockMvcTest {


    @TestConfiguration
    static class JsonPrettyPrintConfig{
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
            return builder -> builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldDeliverListWithBooksOnGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.equalTo("Design Patterns")))
                .andExpect(jsonPath("$[0].author", Matchers.equalTo("Erich Gamma")))
                .andExpect(jsonPath("$[1].title", Matchers.equalTo("Clean Code")));

    }

    @Test
    void shouldDeliverListWithBooksOnGetAll2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book")).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        System.out.println(json);
        List<Book> books = objectMapper.readValue(json, new TypeReference<>() {});
        MatcherAssert.assertThat(books,hasSize(3));
        MatcherAssert.assertThat(books.get(0).getTitle(),equalTo("Design Patterns"));
    }


    @Test
    void shouldCreateNewBook() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)));


        String isbn = "123-121313";
        String title = "Foo and Bar";
        String author = "Tim Timster";
        String description = "Lorem Ipsum";
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[3].title", Matchers.equalTo("Foo and Bar")))
                .andExpect(jsonPath("$[3].author", Matchers.equalTo("Tim Timster")));
    }
}
