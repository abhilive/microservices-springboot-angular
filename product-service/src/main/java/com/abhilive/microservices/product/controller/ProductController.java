package com.abhilive.microservices.product.controller;

import com.abhilive.microservices.product.dto.ProductRequest;
import com.abhilive.microservices.product.dto.ProductResponse;
import com.abhilive.microservices.product.model.Product;
import com.abhilive.microservices.product.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        try {
//            Thread.sleep(5000);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        return productService.getAllProducts();
    }
}
