package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.Book;
import com.epolsoft.wtr.repository.BookRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static Book p1;
    private static Book p2;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Book("C++");
        p2 = new Book("C#");
    }

    @Test
    public void findById() {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(p1));
        assertThat(bookService.findById(1), is(Optional.of(p1)));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void createOrUpdate() {
        Mockito.when(bookRepository.save(p1)).thenReturn(p1);
        assertThat(bookService.createOrUpdate(p1), is(p1));
        Mockito.verify(bookRepository, Mockito.times(1)).save(p1);
        Mockito.when(bookRepository.save(p2)).thenReturn(p2);
        assertThat(bookService.createOrUpdate(p2).getName(), is(p2.getName()));
        Mockito.verify(bookRepository, Mockito.times(1)).save(p2);
    }

    @Test
    void createOrUpdate_EmptyString() {
        Book book = new Book("");
        assertThrows(ValidationException.class, () -> {
            bookService.createOrUpdate(book);
        });
        Mockito.verify(bookRepository, Mockito.times(0)).save(book);
    }

    @Test
    void deleteById() {
        bookService.deleteById(1);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1);
    }
}
