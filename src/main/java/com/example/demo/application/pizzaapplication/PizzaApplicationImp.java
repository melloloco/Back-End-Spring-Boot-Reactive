package com.example.demo.application.pizzaapplication;

import java.util.UUID;

import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.ingredientdomain.IngredientWriteRepository;
import com.example.demo.domain.pizzadomain.Pizza;
import com.example.demo.domain.pizzadomain.PizzaWriteRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PizzaApplicationImp extends ApplicationBase<Pizza, UUID> implements PizzaApplication {
    private final PizzaWriteRepository pizzaWriteRepository;
    private final IngredientWriteRepository ingredientWriteRepository;
    //private final ImageApplicationImp imageApplicationImp;
    private final ModelMapper modelMapper;
    private final Logger logger;

    @Autowired
    public PizzaApplicationImp(final PizzaWriteRepository pizzaWriteRepository,
            final IngredientWriteRepository ingredientWriteRepository, final Logger logger, final ModelMapper modelMapper) {

        super((id) -> pizzaWriteRepository.findById(id));

        this.pizzaWriteRepository = pizzaWriteRepository;
        this.ingredientWriteRepository = ingredientWriteRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
    }

    public Mono<PizzaDTOOut> add(PizzaDTOIn pizzaDTOIn) {
        Pizza pizza = this.modelMapper.map(pizzaDTOIn, Pizza.class);
        pizza.setId(UUID.randomUUID());
        pizza.setThisNew(true);
        pizza.setImage(pizza.getImage());

        Flux.just(pizzaDTOIn.getIngredients())
        .flatMap(setId -> 
            this.ingredientWriteRepository.findByIdBadRequest(setId.iterator().next())
            .flatMap(dbIngredient -> {
                pizza.addIngredient(dbIngredient);
                pizza.setPrice(pizza.calculatePrice());
                return Mono.just(dbIngredient);
            })
            ).subscribe();

        return pizza.validate("name", pizza.getName(), (name) -> this.pizzaWriteRepository.exists(name))
        .then(this.pizzaWriteRepository.add(pizza)).flatMap(monoPizza -> {
            logger.info(this.serializeObject(pizza, "added"));
            return Mono.just(this.modelMapper.map(pizza, PizzaDTOOut.class));
        });

    }

}
