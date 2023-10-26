package com.sudoo.storageservice.service;

import com.sudo248.domain.base.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseEntity<?> uploadFile(MultipartFile file);

    ResponseEntity<?> downloadFile(String fileName);
}
