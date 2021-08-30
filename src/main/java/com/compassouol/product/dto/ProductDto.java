package com.compassouol.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotEmpty(message = "Não pode ser vazio!")
    private String name;
    @NotEmpty(message = "Não pode ser vazio!")
    private String description;
    @NotNull
    @DecimalMin("0.01")
    private Double price;
}
