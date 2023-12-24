package com.ryanlou.springmall.service;

import com.ryanlou.springmall.dto.CreateOrderRequest;
import com.ryanlou.springmall.dto.OrderQueryParam;
import com.ryanlou.springmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId , CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParam orderQueryParam);

    Integer countOrders(OrderQueryParam orderQueryParam);
}
