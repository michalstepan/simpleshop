package com.michalstepan.simpleshop.integration;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductChangesIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void productPriceChangeWontChangeOrdersPriceTest() throws Exception {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(10));
        product1.setName("jacket");

        long productId = productService.create(product1);
        product1.setId(productId);

        assertEquals(14, productService.getAll().size());

        final DateTime beforeOrderPlaced = DateTime.now();
        final PlaceOrderDTO orderDTO = new PlaceOrderDTO("michalstepan92@gmail.com", List.of(productId));
        final Order order = orderService.placeOrder(orderDTO);
        final DateTime afterOrderPlaced = DateTime.now();

        assertEquals(BigDecimal.valueOf(1000, 2), order.getPrice());
        List<Order> orders = orderService.retrieveOrders(beforeOrderPlaced, afterOrderPlaced);
        assertEquals(1, orders.size());
        assertEquals(BigDecimal.valueOf(1000, 2), orders.get(0).getPrice());

        product1.setPrice(BigDecimal.valueOf(20));
        productService.update(product1);
        orders = orderService.retrieveOrders(beforeOrderPlaced, afterOrderPlaced);
        assertEquals(1, orders.size());
        assertEquals(BigDecimal.valueOf(1000, 2), orders.get(0).getPrice());
    }
}
