package com.example.demo.domain.imagedomain;

import java.util.UUID;

public interface ImageRepositoryRedis {

    public Image get(UUID id);

}
