package com.example.demo.application.pizzaapplication;

import reactor.core.publisher.Mono;

public interface PizzaApplication {
    
    public Mono<PizzaDTOOut> add(PizzaDTOIn dto);  
}
