package com.ryanlou.springmall.service.impl;

import com.ryanlou.springmall.dao.OrderDao;
import com.ryanlou.springmall.dao.ProductDao;
import com.ryanlou.springmall.dto.BuyItem;
import com.ryanlou.springmall.dto.CreateOrderRequest;
import com.ryanlou.springmall.model.Order;
import com.ryanlou.springmall.model.OrderItem;
import com.ryanlou.springmall.model.Product;
import com.ryanlou.springmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    @Transactional // 只要有多個table 改變都要加
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId , orderItemList);

        return orderId;
    }
}
