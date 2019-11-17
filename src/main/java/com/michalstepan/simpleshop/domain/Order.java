package com.michalstepan.simpleshop.domain;

import com.michalstepan.simpleshop.domain.entity.OrderEntity;
import com.michalstepan.simpleshop.domain.entity.OrderItemEntity;
import com.michalstepan.simpleshop.domain.validations.OrderPlaceValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Null(groups = OrderPlaceValidation.class)
    private Long id;

    @NotNull(groups = OrderPlaceValidation.class)
    @Pattern(groups = OrderPlaceValidation.class, regexp = ".*@.*")
    private String buyerEmail;

    @NotNull(groups = OrderPlaceValidation.class)
    private List<Long> products;

    @Null(groups = OrderPlaceValidation.class)
    private DateTime created;

    public static Order fromEntity(OrderEntity orderEntity) {
        return new Order(orderEntity.getId(),
                orderEntity.getBuyerEmail(),
                orderEntity.getItems().stream()
                        .map(OrderItemEntity::getId)
                        .collect(Collectors.toList()),
                orderEntity.getCreated());
    }
}
