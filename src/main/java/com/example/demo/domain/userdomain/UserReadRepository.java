package com.example.demo.domain.userdomain;
import reactor.core.publisher.Flux;

public interface UserReadRepository {
    
    public Flux<UserProjection> getAll(String firstname, int page, int size);
}