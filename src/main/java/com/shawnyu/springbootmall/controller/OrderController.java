package com.shawnyu.springbootmall.controller;

import com.shawnyu.springbootmall.dto.OrderQueryParams;
import com.shawnyu.springbootmall.dto.CreateOrderRequest;
import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.Order;
import com.shawnyu.springbootmall.service.OrderService;
import com.shawnyu.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 取得訂單列表
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "8") @Max(1000) @Min(0) Integer limit)
    {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setPage(page);
        orderQueryParams.setLimit(limit);

        // 取得 order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // 取得 order 總數
        Integer total = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> pageObject = new Page<>();
        pageObject.setPage(page);
        pageObject.setLimit(limit);
        pageObject.setTotal(total);
        pageObject.setBooks(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(pageObject);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
