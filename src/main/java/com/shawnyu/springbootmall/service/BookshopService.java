package com.shawnyu.springbootmall.service;

import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.BookRequest;
import com.shawnyu.springbootmall.model.Category;

import java.util.List;

public interface BookshopService {

    List<Book> getBooks(String category);

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);
}
