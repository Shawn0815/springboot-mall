package com.shawnyu.springbootmall.model;

import com.shawnyu.springbootmall.dto.CartItemDTO;

import java.util.List;

public class CartResponse {

    List<CartItemDTO> cartItemList;

    Integer numberOfItems;

    Integer total;

    public List<CartItemDTO> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDTO> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
