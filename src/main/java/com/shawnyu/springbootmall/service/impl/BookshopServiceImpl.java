package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.BookshopDao;
import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.BookRequest;
import com.shawnyu.springbootmall.model.Category;
import com.shawnyu.springbootmall.service.BookshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookshopServiceImpl implements BookshopService {

    @Autowired
    BookshopDao bookshopDao;

    @Override
    public List<Book> getBooks(String category) {
        return bookshopDao.getBooks(category);
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookshopDao.getBookById(bookId);
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        return bookshopDao.createBook(bookRequest);
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        bookshopDao.updateBook(bookId, bookRequest);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        bookshopDao.deleteBookById(bookId);
    }
}
