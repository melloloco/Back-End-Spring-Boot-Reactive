package com.example.demo.domain.ingredientdomain;

import reactor.core.publisher.Flux;

public interface IngredientReadRepository {
    
    public Flux<IngredientProjection> getAll(String name, int page, int size);
}