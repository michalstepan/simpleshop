package com.michalstepan.simpleshop.controller;

import com.michalstepan.simpleshop.domain.Order;
import com.michalstepan.simpleshop.domain.dto.PlaceOrderDTO;
import com.michalstepan.simpleshop.domain.validations.PlaceOrderValidation;
import com.michalstepan.simpleshop.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "place")
    public Order placeOrder(@Validated(PlaceOrderValidation.class) @RequestBody PlaceOrderDTO order) {
        return orderService.placeOrder(order);
    }

    @GetMapping
    public List<Order> retrieveOrders(@RequestParam DateTime from, @RequestParam DateTime to) {
        return orderService.retrieveOrders(from, to);
    }

}
