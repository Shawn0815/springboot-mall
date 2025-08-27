package com.shawnyu.springbootmall.service;

import com.shawnyu.springbootmall.dto.CreateOrderRequest;
import com.shawnyu.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
