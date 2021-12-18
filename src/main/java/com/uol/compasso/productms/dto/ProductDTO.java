package com.uol.compasso.productms.dto;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {
    @Nullable
    private Long id;
    @NotBlank(message = "The field name cannot be null or blanked")
    private String name;
    @NotBlank(message = "The field description cannot be null or blanked")
    private String description;
    @NotNull(message = "The field price cannot be null")
    @Min(value = 0L, message = "The value of price must be positive")
    private Double price;
}
