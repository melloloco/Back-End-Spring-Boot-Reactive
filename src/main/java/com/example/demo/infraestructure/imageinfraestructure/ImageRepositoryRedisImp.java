package com.example.demo.infraestructure.imageinfraestructure;

import java.util.UUID;

import com.example.demo.domain.imagedomain.Image;
import com.example.demo.domain.imagedomain.ImageRepositoryRedis;

import org.springframework.stereotype.Repository;

@Repository
public class ImageRepositoryRedisImp implements ImageRepositoryRedis {

    public Image get(UUID id) {
        return new Image();
    }

    /*
     * public Mono<Image> add(Image image) {
     * return operations.opsForValue()
     * .set(image.getId(), image.getImage()).map(__ -> image);
     * }
     */

}
