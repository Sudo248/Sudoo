package com.sudoo.storageservice.controller.dto;

public class FileDto {
    private final String fileUrl;

    public FileDto(String imageUrl) {
        this.fileUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
