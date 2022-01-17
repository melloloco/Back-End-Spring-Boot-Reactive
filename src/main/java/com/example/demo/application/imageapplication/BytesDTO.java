package com.example.demo.application.imageapplication;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @NoArgsConstructor @Getter @Setter class BytesDTO {
    private UUID id;
    private byte[] image;

    public UUID getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

}