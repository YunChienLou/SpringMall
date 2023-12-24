package com.ryanlou.springmall.dao;

import com.ryanlou.springmall.constant.ProductCategory;
import com.ryanlou.springmall.dto.ProductQueryParam;
import com.ryanlou.springmall.dto.ProductRequest;
import com.ryanlou.springmall.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(ProductQueryParam productQueryParam);

    List<Product> getProducts(ProductQueryParam productQueryParam);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
    void updateStock(Integer productId , Integer stock);

    void deleteProduct(Integer productId);
}
