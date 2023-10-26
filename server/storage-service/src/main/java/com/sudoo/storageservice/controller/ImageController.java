package com.sudoo.storageservice.controller;

import com.sudo248.domain.base.BaseResponse;
import com.sudoo.storageservice.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse<?>> upload(
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam(value = "source", required = false, defaultValue = "cloudinary") String source
    ) {
        return imageService.storeImage(image, source);
    }
}
