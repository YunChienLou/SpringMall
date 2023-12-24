package com.ryanlou.springmall.service;

import com.ryanlou.springmall.dto.CreateOrderRequest;
import com.ryanlou.springmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId , CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
