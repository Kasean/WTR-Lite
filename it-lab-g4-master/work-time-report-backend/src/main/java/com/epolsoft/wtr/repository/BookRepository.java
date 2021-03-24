package com.epolsoft.wtr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByName(String name);
    
    /* not work in Spring till
    @Procedure(Book.NamedQuery_GetListOfBooks)
    List<Book> getListOfBooks(@Param("inName") String name);
    */
    
    @Query(nativeQuery = true,value = "call get_list_of_book(:name)")   // call store procedure with arguments
    List<Book> getListOfBooks(@Param("name") String name);
    
    // название базы и полей надо брать из объекта entity
    @Query("select b from Book b where b.name = :name")
    List<Book> getByName(@Param("name") String name);

    Book findOneById(Integer id);

}

