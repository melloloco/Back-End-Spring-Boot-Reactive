package com.example.demo.infraestructure.pizzainfraestructure;
import java.util.UUID;

import com.example.demo.domain.pizzadomain.Pizza;
import com.example.demo.domain.pizzadomain.PizzaWriteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class PizzaRepositoryImp implements PizzaWriteRepository {

    private PizzaReactiveRepository pizzaReactiveRepository;

    @Autowired
    public PizzaRepositoryImp(PizzaReactiveRepository pizzaReactiveRepository){
        this.pizzaReactiveRepository = pizzaReactiveRepository;
    }

    @Override
    public Mono<Pizza> add(Pizza pizza) {
        return this.pizzaReactiveRepository.save(pizza);
    } 
    
    @Override
    public Mono<Pizza> findById(UUID id) {
        return this.pizzaReactiveRepository.findById(id);
    }

    @Override
    public Mono<Integer> exists(String name) {
        return this.pizzaReactiveRepository.existsByName(name);
    }
}
