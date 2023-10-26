package com.sudoo.storageservice.service.impl;

import com.sudo248.domain.base.BaseResponse;
import com.sudoo.storageservice.controller.dto.FileDto;
import com.sudoo.storageservice.service.FileService;
import com.sudoo.storageservice.uitls.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${store.file.path}")
    private String storeFilePath;

    final ServletContext servletContext;

    public FileServiceImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            Path storePath = Paths.get(storeFilePath);
            Path imagePath = storePath.resolve(fileName);
            Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            return BaseResponse.ok(new FileDto(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> downloadFile(String fileName) {
        try {
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
            Path storePath = Paths.get(storeFilePath);
            Path filePath = storePath.resolve(fileName);
            final File file = filePath.toFile();

            final InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return BaseResponse.status(HttpStatus.BAD_REQUEST, "Not found file " + fileName);
        }
    }
}
