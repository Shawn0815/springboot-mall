package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.BookDao;
import com.shawnyu.springbootmall.dao.OrderDao;
import com.shawnyu.springbootmall.dao.UserDao;
import com.shawnyu.springbootmall.dto.BuyItem;
import com.shawnyu.springbootmall.dto.CreateOrderRequest;
import com.shawnyu.springbootmall.model.Book;
import com.shawnyu.springbootmall.model.Order;
import com.shawnyu.springbootmall.model.OrderItem;
import com.shawnyu.springbootmall.model.User;
import com.shawnyu.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    // 創建一個 Log 出來（括號內填入這個 class 的名稱）
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override


    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 檢查 user 是否存在
        User user = userDao.getUserbyId(userId);
        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        // 依序取出每本書
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()) {
            Book book = bookDao.getBookById(buyItem.getBookId());

            // 檢查商品是否存在、庫存是否足夠
            if (book == null) {
                log.warn("商品 {} 不存在", buyItem.getBookId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if (book.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存不足，無法購買。剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getBookId(), book.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            bookDao.updateStock(book.getBookId(), book.getStock() - buyItem.getQuantity());

            // 增加商品銷售書量
            bookDao.updateSalesCount(book.getBookId(), book.getSalesCount() + buyItem.getQuantity());

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
