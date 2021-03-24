package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.entity.Book;
import com.epolsoft.wtr.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest 
{
    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;
       
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAddEmployee() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Book book = new Book();
        book.setId(1);

        when(bookService.createOrUpdate(any(Book.class))).thenReturn(book);
         
        Book bookToAdd = new Book("Lokesh");
        ResponseEntity<Object> responseEntity = bookController.addBook(bookToAdd);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }
 
    
    @Test
    public void testFindAll() 
    {
        // given
        Book book1 = new Book("Lokesh");
        Book book2 = new Book("Alex"); 
        
        List<Book> list = new ArrayList<Book>();
        list.addAll(Arrays.asList(book1, book2));
 
        when(bookService.findAll()).thenReturn(list);
 
        // when
        List<Book> result = bookController.getAllBooks();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getName())
                        .isEqualTo(book1.getName());

        assertThat(result.get(1).getName())
                        .isEqualTo(book2.getName());
    }
    
}