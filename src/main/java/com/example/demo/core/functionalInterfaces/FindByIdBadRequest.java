package com.example.demo.core.functionalInterfaces;

import reactor.core.publisher.Mono;

public interface FindByIdBadRequest<T, ID> {
    public Mono<T> findByIdBadRequest(ID id);
}
