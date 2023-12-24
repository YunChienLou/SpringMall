package com.ryanlou.springmall.dao;

import com.ryanlou.springmall.model.Order;
import com.ryanlou.springmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId , List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
