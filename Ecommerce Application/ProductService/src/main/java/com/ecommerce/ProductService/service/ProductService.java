package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.model.ProductRequest;
import com.ecommerce.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest  productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    void deleteProduct(long productId);
}
