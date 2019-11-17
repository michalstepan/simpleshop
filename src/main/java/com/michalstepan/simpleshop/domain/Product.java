package com.michalstepan.simpleshop.domain;

import com.michalstepan.simpleshop.domain.validations.ProductCreateValidation;
import com.michalstepan.simpleshop.domain.validations.ProductUpdateValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotNull(groups = ProductUpdateValidation.class)
    @Null(groups = ProductCreateValidation.class)
    private Long id;

    @NonNull
    @NotNull(groups = {ProductCreateValidation.class, ProductUpdateValidation.class})
    private String name;

    @NonNull
    @NotNull(groups = {ProductCreateValidation.class, ProductUpdateValidation.class})
    private BigDecimal price;
}
