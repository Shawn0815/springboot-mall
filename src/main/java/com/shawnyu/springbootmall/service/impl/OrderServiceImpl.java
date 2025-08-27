package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.BookDao;
import com.shawnyu.springbootmall.dao.OrderDao;
import com.shawnyu.springbootmall.dto.BuyItem;
import com.shawnyu.springbootmall.dto.CreateOrderRequest;
import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.OrderItem;
import com.shawnyu.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        // 依序取出每本書
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()) {
            Book book = bookDao.getBookById(buyItem.getBookId());

            // 計算總價錢
            int amount = buyItem.getQuantity() * book.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(buyItem.getBookId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setPrice(book.getPrice());
            orderItem.setAmount(totalAmount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
