package com.example.demo.infraestructure.ingredientrepository;

import java.util.UUID;

import com.example.demo.domain.ingredientdomain.Ingredient;
import com.example.demo.domain.ingredientdomain.IngredientProjection;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IngredientReactiveRepository extends ReactiveCrudRepository<Ingredient, UUID> {

    @Query("SELECT i.id, i.name, i.price FROM ingredients i WHERE (:name is NULL OR i.name LIKE concat('%', :name, '%')) limit :size offset :page")
    Flux<IngredientProjection> findByCriteria(@Param("name") String name, int size, int page);

    @Query("SELECT CASE WHEN count(id)>0 THEN 1 ELSE 0 END FROM ingredients WHERE name = :name")
    Mono<Integer> existsByName(@Param("name") String name);

}