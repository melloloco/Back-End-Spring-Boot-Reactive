package com.example.demo.infraestructure.ingredientrepository;

import java.util.UUID;

import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.domain.ingredientdomain.Ingredient;
import com.example.demo.domain.ingredientdomain.IngredientProjection;
import com.example.demo.domain.ingredientdomain.IngredientReadRepository;
import com.example.demo.domain.ingredientdomain.IngredientWriteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class IngredientRepositoryImp implements IngredientWriteRepository, IngredientReadRepository {

    private final IngredientReactiveRepository ingredientReactiveRepository;

    @Autowired
    public IngredientRepositoryImp(IngredientReactiveRepository ingredientReactiveRepository) {
        this.ingredientReactiveRepository = ingredientReactiveRepository;
    }

    @Override
    public Mono<Ingredient> add(Ingredient ingredient) {
        return this.ingredientReactiveRepository.save(ingredient);
    }

    @Override
    public Mono<Ingredient> findById(UUID id) {
        return this.ingredientReactiveRepository.findById(id);
    }

    @Override
    public Mono<Ingredient> findByIdBadRequest(UUID id) {
        return this.ingredientReactiveRepository.findById(id).switchIfEmpty(Mono.error(new BadRequestException()));
    }

    @Override
    public Mono<Ingredient> update(Ingredient ingredient) {
        return this.ingredientReactiveRepository.save(ingredient);
    }

    @Override
    public Mono<Void> delete(Ingredient ingredient) {
        return this.ingredientReactiveRepository.delete(ingredient);
    }

    @Override
    public Flux<IngredientProjection> getAll(String name, int page, int size) {
        return this.ingredientReactiveRepository.findByCriteria(name, size, page);
    }

    @Override
    public Mono<Integer> exists(String name) {
        return this.ingredientReactiveRepository.existsByName(name);
    }

}
