package com.michalstepan.simpleshop.domain;

import com.michalstepan.simpleshop.domain.entity.OrderEntity;
import com.michalstepan.simpleshop.domain.validations.PlaceOrderValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Null(groups = PlaceOrderValidation.class)
    private Long id;

    @NotNull(groups = PlaceOrderValidation.class)
    @Pattern(groups = PlaceOrderValidation.class, regexp = ".*@.*")
    private String buyerEmail;

    @NotNull(groups = PlaceOrderValidation.class)
    private List<Product> products;

    @Null(groups = PlaceOrderValidation.class)
    private DateTime created;

    public static Order fromEntity(OrderEntity orderEntity) {
        return new Order(orderEntity.getId(),
                orderEntity.getBuyerEmail(),
                orderEntity.getItems().stream().map(i -> {
                    Product product = new Product();
                    product.setId(i.getProductId());
                    product.setPrice(i.getPrice());
                    return product;
                }).collect(Collectors.toList()),
                orderEntity.getCreated());
    }

    public BigDecimal getPrice() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Price for order cannot be calculated."));
    }
}
