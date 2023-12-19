package com.sudoo.storageservice.service;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.base.BaseService;
import com.sudoo.storageservice.config.StorageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends BaseService {

    ResponseEntity<?> uploadFile(MultipartFile file, StorageSource source);

    ResponseEntity<?> downloadFile(String fileName);
}
