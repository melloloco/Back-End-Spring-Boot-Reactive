package com.example.demo.application.imageapplication;

import java.io.IOException;
import java.util.UUID;

import com.example.demo.domain.imagedomain.Image;
import com.example.demo.domain.imagedomain.ImageRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ImageApplicationImp implements ImageApplication {

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageApplicationImp(final ImageRepository imageRepository, final ModelMapper modelMapper) {

        // super((id) -> imageRepository.get(id));
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    public Mono<ImageDTO> add(CreateOrUpdateImageDTO dto) throws IOException {
        Image image = modelMapper.map(dto, Image.class);
        image.setId(UUID.randomUUID());
        image.setThisNew(true);
        return this.imageRepository.add(image)
                .flatMap(monoImage -> Mono.just(this.modelMapper.map(monoImage, ImageDTO.class)));
    }

    @Override
    public Mono<BytesDTO> get(UUID id) {
        return null;
    }

}
