package com.shawnyu.springbootmall.service.impl;

import com.shawnyu.springbootmall.dao.ProductDao;
import com.shawnyu.springbootmall.model.Product;
import com.shawnyu.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProdcutById(productId);
    }
}
