package com.michalstepan.simpleshop.unit;

import com.michalstepan.simpleshop.domain.Product;
import com.michalstepan.simpleshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductTests {

    @Autowired
    private ProductService productService;

    @Test
    public void createProductTest() throws Exception {
        Product p1 = new Product();
        p1.setPrice(BigDecimal.valueOf(10));
        p1.setName("shoes");

        long createdId = productService.create(p1);
        p1.setId(createdId);
        assertEquals(3, productService.getAll().size());
    }

    @Test
    public void updateProductTest() throws Exception {
        Product p1 = new Product();
        p1.setPrice(BigDecimal.valueOf(10));
        p1.setName("shoes");

        long createdId = productService.create(p1);
        p1.setId(createdId);
        assertEquals(4, productService.getAll().size());
        assertEquals(BigDecimal.valueOf(1000, 2), productService.getAll().stream()
                .filter(p -> Objects.equals(p.getId(), p1.getId()))
                .findFirst()
                .map(Product::getPrice)
                .orElseThrow(() -> new RuntimeException("Cannot happen.")));

        p1.setPrice(BigDecimal.valueOf(20));
        productService.update(p1);
        assertEquals(4, productService.getAll().size());
        assertEquals(BigDecimal.valueOf(2000, 2), productService.getAll().stream()
                .filter(p -> Objects.equals(p.getId(), p1.getId()))
                .findFirst()
                .map(Product::getPrice)
                .orElseThrow(() -> new RuntimeException("Cannot happen.")));
    }
}
