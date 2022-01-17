package com.example.demo.application.userapplication;
import java.util.UUID;
import javax.validation.Valid;
import com.example.demo.domain.userdomain.UserProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserApplication {
    public Mono<UserDTOOut> add(@Valid UserDTOIn userDTOIn);
    public Mono<UserDTOIn> get(UUID id);
    public Mono<UserDTOOut> update(UUID id, UpdateDTO updateDTO);
    public Flux<UserProjection> getAll(String firstname,  int page, int size);
}
