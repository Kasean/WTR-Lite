package com.epolsoft.wtr.entity;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@Entity
@Table(name = "book")

@NamedStoredProcedureQueries({
   @NamedStoredProcedureQuery(name = Book.NamedQuery_FindAndDeleteBook, 
                              procedureName = "find_and_delete_book",
                              parameters = {
                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "inName", type = String.class)
                              }),
   @NamedStoredProcedureQuery(name = Book.NamedQuery_GetListOfBooks, 
                              procedureName = "get_list_of_book",
                              resultClasses = { Book.class },
                              parameters = {
                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "inName", type = String.class)
                              })
})
public class Book {
    
    public static final String NamedQuery_FindAndDeleteBook = "findAndDeleteBook";
    public static final String NamedQuery_GetListOfBooks = "getListOfBooks";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId", nullable = false)
    private Integer id;

    @Column(name = "bookName", length = 999, nullable = false)
    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + "]";
    }
}
