package com.ryanlou.springmall.dao;

import com.ryanlou.springmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
