package com.sudoo.storageservice.service;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.base.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService extends BaseService {
    ResponseEntity<BaseResponse<?>> storeImage(MultipartFile image, String source);
}
