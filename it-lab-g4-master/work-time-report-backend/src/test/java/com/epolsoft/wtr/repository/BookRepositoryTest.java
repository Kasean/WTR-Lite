package com.epolsoft.wtr.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;

import com.epolsoft.wtr.entity.Book;

@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
     config = @SqlConfig(separator = "##") )
public class BookRepositoryTest {

    private static Book p1;
    private static Book p2;
    private static Book p1_modified;

    @Autowired
    private BookRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new Book(1, "C++");
        p2 = new Book(2, "C#");
        p1_modified = new Book(1, "Java");
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findById() {
        assertThat(repository.findById(1), is(Optional.of(p1)));
    }

    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByName() {
        List<Book> books = repository.getByName("c#");
        assertThat(books.size(), is(1));
        assertThat(books.get(0), is(p2));
    }*/

    @Test
    @Sql("classpath:test_data.sql")
    public void getByName() {
        List<Book> books = repository.getByName("c#");
        assertThat(books.size(), is(1));
        assertThat(books.get(0), is(p2));
    }
 
    @Test
    @Sql("classpath:test_data.sql")
    public void getListOfBooks() {
        List<Book> books = repository.getListOfBooks("c#");
        assertThat(books.size(), is(1));
        assertThat(books.get(0), is(p2));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        Iterable<Book> iterableBooks = repository.findAll();
        long numberOfBooks = StreamSupport.stream(iterableBooks.spliterator(), false).count();
        assertThat(numberOfBooks, is(2L));
    }

    @Test
    public void save() {
        assertThat(repository.save(p1), is(p1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void update() {
        Optional<Book> book = repository.findById(1);
        if (book.isPresent()) {
            book.get().setName(p1_modified.getName());
            assertThat(repository.save(book.get()), is(p1_modified));
        } else {
            fail("Expected book '" + p1.getName() + "' is not found");
        }
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void count() {
        assertThat(repository.count(), is(2L));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void deleteById() {
    	printBookStatus();
        repository.deleteById(p1.getId());
        printBookStatus();
        assertThat(repository.count(), is(1L));
    }
    
    public void printBookStatus()
    {
        Iterable<Book> result = repository.findAll();
        for(Book s: result)
        {
        	System.out.println(s.toString());
        }   	
    }

}
