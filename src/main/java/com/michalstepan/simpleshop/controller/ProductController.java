package com.michalstepan.simpleshop.controller;

import com.michalstepan.simpleshop.domain.Product;
import com.michalstepan.simpleshop.domain.validations.ProductCreateValidation;
import com.michalstepan.simpleshop.domain.validations.ProductUpdateValidation;
import com.michalstepan.simpleshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("products")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public long create(@Validated(ProductCreateValidation.class) @RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PutMapping("/{id}")
    public void update(@Validated(ProductUpdateValidation.class) @RequestBody Product product,
                       @PathVariable String id) {
        productService.update(product);
    }
}
