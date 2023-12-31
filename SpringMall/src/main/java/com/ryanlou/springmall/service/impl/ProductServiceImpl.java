package com.ryanlou.springmall.service.impl;

import com.ryanlou.springmall.constant.ProductCategory;
import com.ryanlou.springmall.dao.ProductDao;
import com.ryanlou.springmall.dto.ProductQueryParam;
import com.ryanlou.springmall.dto.ProductRequest;
import com.ryanlou.springmall.model.Product;
import com.ryanlou.springmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(ProductQueryParam productQueryParam) {
        return productDao.countProduct(productQueryParam);
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        return productDao.getProducts(productQueryParam);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
