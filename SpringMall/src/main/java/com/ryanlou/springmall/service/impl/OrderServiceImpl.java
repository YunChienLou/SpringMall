package com.ryanlou.springmall.service.impl;

import com.ryanlou.springmall.dao.OrderDao;
import com.ryanlou.springmall.dao.ProductDao;
import com.ryanlou.springmall.dao.impl.UserDaoImpl;
import com.ryanlou.springmall.dto.BuyItem;
import com.ryanlou.springmall.dto.CreateOrderRequest;
import com.ryanlou.springmall.model.Order;
import com.ryanlou.springmall.model.OrderItem;
import com.ryanlou.springmall.model.Product;
import com.ryanlou.springmall.model.User;
import com.ryanlou.springmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDaoImpl userDao;

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

        //檢查user 是否存在
        User user = userDao.getUserById(userId);
        if (user == null){
            log.warn("該 userId {} 不存在" , userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查 product 是否存在 、 庫存是否足夠
            if(product == null){
                log.warn("該 商品 {} 不存在" , buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("該 商品 {} 庫存不足, 無法購買。 剩餘庫存 {} , 欲購買數量 {}" ,
                        buyItem.getProductId() , product.getStock() , buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProduct_id() , product.getStock() - buyItem.getQuantity());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;


            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId , orderItemList);

        return orderId;
    }
}
