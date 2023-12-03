package com.ryanlou.springmall.service.impl;

import com.ryanlou.springmall.dao.ProductDao;
import com.ryanlou.springmall.model.Product;
import com.ryanlou.springmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
