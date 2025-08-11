package com.shawnyu.springbootmall.dao;

import com.shawnyu.springbootmall.model.Product;

public interface ProductDao {
    Product getProdcutById(Integer productId);
}
