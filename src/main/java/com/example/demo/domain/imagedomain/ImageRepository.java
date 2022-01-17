package com.example.demo.domain.imagedomain;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface ImageRepository { // quito reactivecrudrepository

    public Mono<Image> add(Image image);

    public Mono<byte[]> get(UUID id);

}
