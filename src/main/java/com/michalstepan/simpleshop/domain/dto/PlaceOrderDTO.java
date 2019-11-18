package com.michalstepan.simpleshop.domain.dto;

import com.michalstepan.simpleshop.domain.validations.PlaceOrderValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDTO {
    @NotNull(groups = PlaceOrderValidation.class)
    @Pattern(groups = PlaceOrderValidation.class, regexp = ".*@.*")
    private String buyerEmail;

    @NotNull(groups = PlaceOrderValidation.class)
    private List<Long> products;
}
