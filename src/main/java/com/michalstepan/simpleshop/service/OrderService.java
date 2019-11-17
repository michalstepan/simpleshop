package com.michalstepan.simpleshop.service;

import com.michalstepan.simpleshop.domain.Order;
import com.michalstepan.simpleshop.domain.entity.OrderEntity;
import com.michalstepan.simpleshop.domain.entity.OrderItemEntity;
import com.michalstepan.simpleshop.repository.OrderRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public long placeOrder(Order order) {
        List<OrderItemEntity> orderItems = order.getProducts().stream()
                .map(productService::findByIdRaw)
                .map(pe -> new OrderItemEntity(pe.getId(), pe.getPrice()))
                .collect(Collectors.toList());

        OrderEntity orderEntity = new OrderEntity(orderItems, order.getBuyerEmail());
        OrderEntity saved = orderRepository.save(orderEntity);
        return saved.getId();
    }

    public List<Order> retrieveOrders(DateTime from, DateTime to) {
        return orderRepository.findByCreatedBetween(from, to).stream()
                .map(Order::fromEntity)
                .collect(Collectors.toList());
    }
}
