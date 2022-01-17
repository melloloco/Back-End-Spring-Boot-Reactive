package com.example.demo.domain.ingredientdomain;

import java.util.UUID;

import com.example.demo.core.functionalInterfaces.ExistsByField;
import com.example.demo.core.functionalInterfaces.FindById;
import com.example.demo.core.functionalInterfaces.FindByIdBadRequest;

import reactor.core.publisher.Mono;

public interface IngredientWriteRepository extends FindById<Ingredient, UUID>, FindByIdBadRequest<Ingredient, UUID>, ExistsByField {
    public Mono<Ingredient> add(Ingredient ingredient);
    public Mono<Ingredient> update(Ingredient ingredient);
    public Mono<Void> delete(Ingredient ingredient);
}
