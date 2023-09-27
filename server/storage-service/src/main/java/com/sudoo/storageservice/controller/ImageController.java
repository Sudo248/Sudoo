package com.sudoo.storageservice.controller;

import com.sudo248.domain.base.BaseResponse;
import com.sudoo.storageservice.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<?>> upload(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "source", required = false, defaultValue = "sudoo") String source
    ) {
        return imageService.storeImage(image, source);
    }


}
