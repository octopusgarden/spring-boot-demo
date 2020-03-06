package org.demo.readinglist;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.demo.readinglist.database.Book;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class ReadingListApplicationTest {
    @Autowired
    private WebApplicationContext webContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/readinglist/bob"))
               .andExpect(status().isOk())
               .andExpect(view().name("readingList"))
               .andExpect(model().attributeExists("books"))
               .andExpect(model().attribute("books", Matchers.is(Matchers.empty())));
    }

    @Test
    public void postBook() throws Exception {
        final Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("bob");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");

        mockMvc.perform(post("/readinglist/bob").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("title", "BOOK TITLE")
                                                .param("author", "BOOK AUTHOR")
                                                .param("isbn", "1234567890")
                                                .param("description", "DESCRIPTION"))
               .andExpect(status().is3xxRedirection())
               .andExpect(header().string("Location", "/readinglist/bob"));

        mockMvc.perform(get("/readinglist/bob"))
               .andExpect(status().isOk())
               .andExpect(view().name("readingList"))
               .andExpect(model().attributeExists("books"))
               .andExpect(model().attribute("books", hasSize(1)))
               .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
    }
}