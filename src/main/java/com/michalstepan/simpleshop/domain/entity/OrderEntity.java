package com.michalstepan.simpleshop.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static org.joda.time.DateTime.now;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Cascade(CascadeType.ALL)
    @OneToMany
    private List<OrderItemEntity> items;

    private DateTime created;

    private String buyerEmail;

    public OrderEntity(List<OrderItemEntity> items, String buyerEmail) {
        this.items = items;
        this.buyerEmail = buyerEmail;
        this.created = now();
    }
}
