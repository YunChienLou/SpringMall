package com.ryanlou.springmall.service;

import com.ryanlou.springmall.dto.ProductRequest;
import com.ryanlou.springmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
