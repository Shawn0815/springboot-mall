package com.shawnyu.springbootmall.dao.impl;

import com.shawnyu.springbootmall.dao.BookshopDao;
import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.BookRequest;
import com.shawnyu.springbootmall.model.Category;
import com.shawnyu.springbootmall.rowmapper.BookRowMapper;
import com.shawnyu.springbootmall.rowmapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookshopDaoImpl implements BookshopDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Book> getBooks(String category) {
        String sql = "SELECT book_id, title, author, publisher, category, image_url, price, " +
                "stock, is_public, published_date, description, created_date, last_modified_date FROM book WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if (category != null) {
            sql = sql + " AND category = :category";
            map.put("category", category);
        }

        List<Book> bookList = namedParameterJdbcTemplate.query(sql, map, new BookRowMapper());

        return bookList;
    }

    @Override
    public Book getBookById(Integer bookId) {
        String sql = "SELECT book_id, title, author, publisher, category, image_url, price, " +
                "stock, is_public, published_date, description, created_date, last_modified_date FROM book WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        List<Book> bookList = namedParameterJdbcTemplate.query(sql, map, new BookRowMapper());

        if (bookList.size() <= 0) {
            return null;
        }

        return bookList.get(0);
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        String sql = "INSERT INTO book(title, author, publisher, category, image_url, price, stock, " +
                "is_public, published_date, description, created_date, last_modified_date) VALUES " +
                "(:title, :author, :publisher, :category, :image_url, :price, :stock, :is_public, " +
                ":published_date, :description, :created_date, :last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("publisher", bookRequest.getPublisher());
        map.put("category", bookRequest.getCategory());
        map.put("image_url", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("stock", bookRequest.getStock());
        map.put("is_public", bookRequest.getIsPublic());
        map.put("published_date", bookRequest.getPublishedDate());
        map.put("description", bookRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer bookId = keyHolder.getKey().intValue();

        return bookId;
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        String sql = "UPDATE book SET title = :title, author = :author, publisher = :publisher, " +
                "category = :category, image_url = :imageUrl, price = :price, stock = :stock, " +
                "is_public = :isPublic, published_date = :publishedDate, description = :description " +
                "WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId); // 記得要額外設定成參數值
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("publisher", bookRequest.getPublisher());
        map.put("category", bookRequest.getCategory());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("stock", bookRequest.getStock());
        map.put("isPublic", bookRequest.getIsPublic());
        map.put("publishedDate", bookRequest.getPublishedDate());
        map.put("description", bookRequest.getDescription());

        map.put("last_modified_date", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        String sql = "DELETE FROM book WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
