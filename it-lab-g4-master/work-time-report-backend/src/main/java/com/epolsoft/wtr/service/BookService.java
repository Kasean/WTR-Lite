package com.epolsoft.wtr.service;


import com.epolsoft.wtr.entity.Book;
import com.epolsoft.wtr.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book findById(Integer id) {
        // TODO: check on null and return 404
        return bookRepository.findOneById(id);
    }

    public Book createOrUpdate(Book product) {
        if ("".equals(product.getName())) {
            throw new ValidationException("Book cannot have empty name");
        }
        return bookRepository.save(product);
    }

    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
