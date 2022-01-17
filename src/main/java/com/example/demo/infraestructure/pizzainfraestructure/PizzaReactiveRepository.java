package com.example.demo.infraestructure.pizzainfraestructure;
import java.util.UUID;

import com.example.demo.domain.pizzadomain.Pizza;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//import org.springframework.stereotype.Repository;

//import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PizzaReactiveRepository extends ReactiveCrudRepository<Pizza, UUID> {
    //@Query("SELECT i.id, i.name, i.price FROM pizzas i WHERE (:name is NULL OR i.name LIKE concat('%', :name, '%')) limit :size offset :page")
    //Flux<PizzaProjection> findByCriteria(@Param("name") String name, int size, int page);

    @Query("SELECT CASE WHEN count(id)>0 THEN 1 ELSE 0 END FROM pizzas WHERE name = :name")
    Mono<Integer> existsByName(@Param("name") String name);
}
