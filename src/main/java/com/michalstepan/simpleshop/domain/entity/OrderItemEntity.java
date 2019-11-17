package com.michalstepan.simpleshop.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private BigDecimal price;

    public OrderItemEntity(Long productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }
}
