package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.model.ProductRequest;
import com.ecommerce.ProductService.model.ProductResponse;
import com.ecommerce.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

   @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal') || hasAuthority('Customer')")
   @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
   }

   @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity){
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
   }


   @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long productId ){

        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product Deleted Successfully with the Id :"+productId, HttpStatus.OK);
   }




}
