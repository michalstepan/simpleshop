package com.michalstepan.simpleshop.unit;

import com.michalstepan.simpleshop.domain.Order;
import com.michalstepan.simpleshop.domain.Product;
import com.michalstepan.simpleshop.domain.dto.PlaceOrderDTO;
import com.michalstepan.simpleshop.service.OrderService;
import com.michalstepan.simpleshop.service.ProductService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void placeOrderTest() throws Exception {
        Product shoes = createProduct("shoes", 10);
        Product laces = createProduct("laces", 2);
        Product shirt = createProduct("shirt", 5);

        assertEquals(0, orderService.retrieveOrders(DateTime.now(), DateTime.now()).size());

        final DateTime timeBeforeOrderPlaced = DateTime.now();
        final Order finalOrder = createOrder(List.of(shoes, laces, shirt), "michalstepan92@gmail.com");
        final DateTime timeAfterOrderPlaced = DateTime.now();

        assertEquals(1, orderService.retrieveOrders(timeBeforeOrderPlaced, timeAfterOrderPlaced).size());
        assertEquals(BigDecimal.valueOf(1700, 2), finalOrder.getPrice());
    }

    @Test
    public void placeMultipleOrdersTest() throws Exception {
        Product shoes = createProduct("shoes", 10);
        Product laces = createProduct("laces", 2);
        Product shirt = createProduct("shirt", 5);

        assertEquals(0, orderService.retrieveOrders(DateTime.now(), DateTime.now()).size());

        final DateTime timeBeforeOrdersPlaced = DateTime.now();
        createOrder(List.of(shoes, laces, shirt), "michalstepan92@gmail.com");
        createOrder(List.of(laces, shirt), "michalstepan92@gmail.com");
        createOrder(List.of(shirt), "michalstepan92@gmail.com");
        final DateTime timeAfterOrdersPlaced = DateTime.now();

        assertEquals(3, orderService.retrieveOrders(timeBeforeOrdersPlaced, timeAfterOrdersPlaced).size());
    }

    private Product createProduct(String name, long price) {
        Product p1 = new Product();
        p1.setPrice(BigDecimal.valueOf(price));
        p1.setName(name);
        long id = productService.create(p1);
        p1.setId(id);
        return p1;
    }

    private Order createOrder(List<Product> products, String buyerEmail) {
        PlaceOrderDTO order = new PlaceOrderDTO();
        order.setProducts(products.stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        order.setBuyerEmail(buyerEmail);
        return orderService.placeOrder(order);
    }
}
