package com.neo.buysell.model.dto;

import org.springframework.http.MediaType;

public class ImageDTO {
    public byte[] bytes;
    public MediaType mediaType;

    public ImageDTO() {

    }

    public ImageDTO(byte[] bytes, MediaType mediaType) {
        this.bytes = bytes;
        this.mediaType = mediaType;
    }

    public static MediaType toMediatype(String filePath) {
        int index = filePath.lastIndexOf(".");
        String extension = filePath.substring(index);
        switch (extension) {
            case ".jpeg":
                return MediaType.IMAGE_JPEG;
            case ".png":
                return MediaType.IMAGE_PNG;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
