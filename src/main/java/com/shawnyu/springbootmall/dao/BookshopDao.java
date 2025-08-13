package com.shawnyu.springbootmall.dao;

import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.BookRequest;
import com.shawnyu.springbootmall.model.Category;

import java.util.List;

public interface BookshopDao {

    List<Book> getBooks(String category);

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);
}
