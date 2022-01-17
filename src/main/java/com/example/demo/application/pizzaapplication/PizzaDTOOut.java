package com.example.demo.application.pizzaapplication;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.demo.domain.ingredientdomain.Ingredient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @Getter @Setter @NoArgsConstructor class PizzaDTOOut {
    private UUID id;
    
    private String name;

    private UUID image;

    private BigDecimal price;

    private Set<Ingredient> Ingredients = new HashSet<Ingredient>();
}
