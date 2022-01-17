package com.example.demo.application.ingredientapplication;

import java.util.UUID;

import com.example.demo.domain.ingredientdomain.IngredientProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface IngredientApplication {
    public Mono<IngredientDTOOut> add(IngredientDTOIn ingredientDTOIn);
    public Mono<IngredientDTOOut> get(UUID id);
    public Mono<IngredientDTOOut> update(UUID id, IngredientDTOIn ingredientDTOIn);
    public Mono<IngredientDTOOut> delete(UUID id);
    public Flux<IngredientProjection> getAll(String name,  int page, int size);
}
