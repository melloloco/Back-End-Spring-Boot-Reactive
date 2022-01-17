package com.example.demo.application.ingredientapplication;

import java.util.UUID;

import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.ingredientdomain.Ingredient;
import com.example.demo.domain.ingredientdomain.IngredientProjection;
import com.example.demo.domain.ingredientdomain.IngredientReadRepository;
import com.example.demo.domain.ingredientdomain.IngredientWriteRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngredientApplicationImp extends ApplicationBase<Ingredient, UUID> implements IngredientApplication{

    private final IngredientWriteRepository ingredientWriteRepository;
    private final IngredientReadRepository ingredientReadRepository;
    private final ModelMapper modelMapper;
    private final Logger logger;

    @Autowired
    public IngredientApplicationImp (final IngredientWriteRepository ingredientWriteRepository, final IngredientReadRepository ingredientReadRepository, 
    final ModelMapper modelMapper, final Logger logger){
        super((id) -> ingredientWriteRepository.findById(id));

        this.ingredientReadRepository = ingredientReadRepository;
        this.ingredientWriteRepository = ingredientWriteRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
    }

    @Override
    public Mono<IngredientDTOOut> add(IngredientDTOIn ingredientDTOIn) {
        Ingredient ingredient = modelMapper.map(ingredientDTOIn, Ingredient.class);
        ingredient.setId(UUID.randomUUID());
        ingredient.setThisNew(true);
        return ingredient.validate("name", ingredient.getName(), (name) -> this.ingredientWriteRepository.exists(name))
        .then(this.ingredientWriteRepository.add(ingredient))
        .flatMap(entity -> {
            logger.info(this.serializeObject(entity, "added")); 
            return Mono.just(this.modelMapper.map(entity, IngredientDTOOut.class));
        });
    }

    @Override
    public Mono<IngredientDTOOut> get(UUID id) {
        return this.findById(id).flatMap( dbingredient -> Mono.just(this.modelMapper.map(dbingredient, IngredientDTOOut.class)));
    }

    @Override
    public Mono<IngredientDTOOut> update(UUID id, IngredientDTOIn ingredientDTOIn) {
        return this.findById(id).flatMap( dbIngredient -> {
            if(dbIngredient.getName().equals(ingredientDTOIn.getName())){
                this.modelMapper.map(ingredientDTOIn, dbIngredient);
                dbIngredient.validate();
                return this.ingredientWriteRepository.update(dbIngredient).flatMap(ingredient -> Mono.just(this.modelMapper.map(ingredient, IngredientDTOOut.class)));
            } else{
                this.modelMapper.map(ingredientDTOIn, dbIngredient);
                return dbIngredient.validate("name", dbIngredient.getName(), (name) -> this.ingredientWriteRepository.exists(name))
                .then(this.ingredientWriteRepository.update(dbIngredient))
                .flatMap(ingredient -> {
                    logger.info(this.serializeObject(dbIngredient, "updated")); 
                    return Mono.just(this.modelMapper.map(ingredient, IngredientDTOOut.class));
                });
            }   
        });
    } 

    @Override
    public Mono<IngredientDTOOut> delete(UUID id) {
        return this.findById(id).flatMap(ingredient -> {
            logger.info(this.serializeObject(ingredient, "deleted")); 
            return this.ingredientWriteRepository.delete(ingredient)
            .then(Mono.just(this.modelMapper.map(ingredient, IngredientDTOOut.class)));
        });
    }

    @Override
    public Flux<IngredientProjection> getAll(String name, int page, int size) {
        return this.ingredientReadRepository.getAll(name, page, size);
    }
}


