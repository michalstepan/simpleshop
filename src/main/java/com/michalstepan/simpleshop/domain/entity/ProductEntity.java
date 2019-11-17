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
public class ProductEntity {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private BigDecimal price;

    public ProductEntity(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
