package com.example.demo.controller.ingredientcontroller.imagecontroller;

import java.io.IOException;

import com.example.demo.application.imageapplication.CreateOrUpdateImageDTO;
import com.example.demo.application.imageapplication.ImageApplication;
import com.example.demo.application.imageapplication.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    public final ImageApplication imageApplication;

    @Autowired
    public ImageController(final ImageApplication imageApplication) {
        this.imageApplication = imageApplication;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ImageDTO> upload(@RequestParam("image") MultipartFile file) throws IOException {
        CreateOrUpdateImageDTO dto = new CreateOrUpdateImageDTO();
        dto.setContent(file.getBytes());
        Mono<ImageDTO> imageDTO = imageApplication.add(dto);
        return imageDTO;

    }
}