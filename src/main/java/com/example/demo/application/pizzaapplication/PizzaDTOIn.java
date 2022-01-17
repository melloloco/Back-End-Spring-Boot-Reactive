package com.example.demo.application.pizzaapplication;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
public @Getter @Setter class PizzaDTOIn {

    @NotBlank
    public String name;

    @NotNull
    public UUID image;

    @NotEmpty
    public Set<UUID> Ingredients;
}
