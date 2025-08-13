package com.shawnyu.springbootmall.controller;

import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.BookRequest;
import com.shawnyu.springbootmall.model.Category;
import com.shawnyu.springbootmall.service.BookshopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookshopController {

    @Autowired
    BookshopService bookshopService;

    // 取得所有書籍（可篩選）
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String category) {
        List<Book> bookList = bookshopService.getBooks(category);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    // 取得特定 id 的書籍
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
        Book book = bookshopService.getBookById(bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    // 新增書籍
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookRequest bookRequest) {
        Integer bookId = bookshopService.createBook(bookRequest);

        Book book = bookshopService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // 更新特定 id 的書籍
    @PutMapping("books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId,
                                           @RequestBody @Valid BookRequest bookRequest) {

        // 檢查 book 是否存在
        Book book = bookshopService.getBookById(bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(book);
        }

        bookshopService.updateBook(bookId, bookRequest);

        Book updatedBook = bookshopService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    // 刪除特定 id 的書籍
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        bookshopService.deleteBookById(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
