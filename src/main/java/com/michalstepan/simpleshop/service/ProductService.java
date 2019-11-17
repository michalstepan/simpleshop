package com.michalstepan.simpleshop.service;

import com.michalstepan.simpleshop.domain.Product;
import com.michalstepan.simpleshop.domain.entity.ProductEntity;
import com.michalstepan.simpleshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static Product transform(ProductEntity pe) {
        return new Product(pe.getId(), pe.getName(), pe.getPrice());
    }

    public long create(Product product) {
        ProductEntity saved = productRepository.save(new ProductEntity(product.getName(), product.getPrice()));
        return saved.getId();
    }

    public List<Product> getAll() {
        return productRepository.findAll().stream()
                .map(ProductService::transform)
                .collect(Collectors.toList());
    }

    public void update(Product product) {
        ProductEntity productEntity = findByIdRaw(product.getId());
        productEntity.setPrice(product.getPrice());
        productRepository.save(productEntity);
    }

    public ProductEntity findByIdRaw(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " does not exists"));
    }
}
