package com.ecommerce.ProductService.service;


import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exception.ProductServiceCustomException;
import com.ecommerce.ProductService.model.ProductRequest;
import com.ecommerce.ProductService.model.ProductResponse;
import com.ecommerce.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product...");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity()).build();
        log.info("Product Added");

        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product : {}",productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with given id is not found.","Product_Not_Found"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantity {} for Id {}", quantity, productId);

        Product product = productRepository.findById(productId).orElseThrow(()->
                new ProductServiceCustomException("Product not found for the given id","PRODUCT_NOT_FOUND")
        );

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product doesn't have sufficient quantity",
                    "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product quantity reduced.");

    }
}
