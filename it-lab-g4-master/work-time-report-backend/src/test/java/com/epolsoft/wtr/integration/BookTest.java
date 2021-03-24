package com.epolsoft.wtr.integration;

import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Book;
import com.epolsoft.wtr.repository.BookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {WtrApplication.class})
// @ActiveProfiles("test") TODO: now 'wtrlite' DB is used instead of 'wtrlite_test' (in all tests)
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql", config = @SqlConfig(separator = "##"))
public class BookTest extends BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookTest.class);
    private static final Integer EXISTING_BOOK_ID = 1;

    @Autowired
    private BookRepository bookRepo;

    @Test
    @Sql("classpath:test_data.sql")
    public void createBook_shouldOK() throws Exception {
        long numberOfBooksBeforeTest = bookRepo.count();
        LOGGER.info("Number of books before test: " + numberOfBooksBeforeTest);

        Book bookToAdd = new Book();
        bookToAdd.setName("BookToAdd#1");

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/book")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(bookToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/book/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfBookAfterTest = bookRepo.count();
        LOGGER.info("Number of books after test: " + numberOfBookAfterTest);
        assertThat(numberOfBookAfterTest, is(numberOfBooksBeforeTest + 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getBookById_shouldOK() throws Exception {
        Book existingBook = bookRepo.findOneById(EXISTING_BOOK_ID);
        assertThat(existingBook, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/book/{ID}", EXISTING_BOOK_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Book obtainedBook = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Book.class);

        assertThat(obtainedBook, is(existingBook));
    }

    @Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getBookById_should404() throws Exception {
        Integer notExistingBookId = 999;
        Book notExistingBook = bookRepo.findOneById(notExistingBookId);
        assertThat(notExistingBook, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/book/{ID}", notExistingBookId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getBooks_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/book")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Book> books = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Book[].class));

        assertThat(books.size(), greaterThanOrEqualTo(2));
    }
}
