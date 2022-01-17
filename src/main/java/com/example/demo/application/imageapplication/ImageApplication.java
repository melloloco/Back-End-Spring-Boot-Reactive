package com.example.demo.application.imageapplication;

import java.io.IOException;
import java.util.UUID;

import reactor.core.publisher.Mono;

public interface ImageApplication {

    public Mono<BytesDTO> get(UUID id);

    public Mono<ImageDTO> add(CreateOrUpdateImageDTO dto) throws IOException;

}