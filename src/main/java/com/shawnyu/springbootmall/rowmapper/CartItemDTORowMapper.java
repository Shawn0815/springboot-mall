package com.shawnyu.springbootmall.rowmapper;

import com.shawnyu.springbootmall.model.CartItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemRowMapper implements RowMapper<CartItem> {

    @Override
    public CartItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
        cartItem.setUserId(resultSet.getInt("user_id"));
        cartItem.setBookId(resultSet.getInt("book_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));

        cartItem.setTitle(resultSet.getString("title"));
        cartItem.setImageUrl(resultSet.getString("image_url"));
        cartItem.setPrice(resultSet.getInt("price"));
        cartItem.setStock(resultSet.getInt("stock"));

        return cartItem;
    }
}
