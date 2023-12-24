package com.ryanlou.springmall.controller;

import com.ryanlou.springmall.dto.CreateOrderRequest;
import com.ryanlou.springmall.dto.OrderQueryParam;
import com.ryanlou.springmall.model.Order;
import com.ryanlou.springmall.model.Product;
import com.ryanlou.springmall.service.OrderService;
import com.ryanlou.springmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            //排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            //分頁 PAGINATION
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset

    ){
        OrderQueryParam orderQueryParam = new OrderQueryParam();
        orderQueryParam.setUserId(userId);
        orderQueryParam.setOrderBy(orderBy);
        orderQueryParam.setSort(sort);
        orderQueryParam.setLimit(limit);
        orderQueryParam.setOffset(offset);

        //取得 orderlist
        List<Order> orderList =  orderService.getOrders(orderQueryParam);
        //取得總比數
        Integer total = orderService.countOrders(orderQueryParam);

        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId ,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
