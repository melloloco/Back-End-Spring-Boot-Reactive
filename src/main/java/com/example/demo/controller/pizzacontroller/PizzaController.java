package com.example.demo.controller.pizzacontroller;

import javax.validation.Valid;

import com.example.demo.application.pizzaapplication.PizzaApplication;
import com.example.demo.application.pizzaapplication.PizzaDTOIn;
import com.example.demo.application.pizzaapplication.PizzaDTOOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pizzas")
@ResponseStatus(HttpStatus.CREATED)
public class PizzaController {
    private final PizzaApplication pizzaApplication;

    @Autowired
    public PizzaController(PizzaApplication pizzaApplication){
        this.pizzaApplication = pizzaApplication;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PizzaDTOOut> create(@Valid @RequestBody PizzaDTOIn pizzaDTOIn){
        
        return this.pizzaApplication.add(pizzaDTOIn);
    }
}
