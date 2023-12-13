package com.ryanlou.springmall.dao;

import com.ryanlou.springmall.dto.ProductRequest;
import com.ryanlou.springmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
