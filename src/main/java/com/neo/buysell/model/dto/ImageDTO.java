package com.neo.buysell.model.dto;

import com.neo.buysell.model.exception.particular.RuntimeIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ImageDTO {
    public byte[] bytes;
    public MediaType mediaType;
    public String path;

    private static final Logger LOG = LoggerFactory.getLogger(ImageDTO.class);

    public ImageDTO(byte[] bytes, MediaType mediaType, String path) {
        this.bytes = bytes;
        this.mediaType = mediaType;
        this.path = path;
    }

    public static MediaType toMediatype(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf(".");
            String extension = fileName.substring(index);
            switch (extension) {
                case ".jpeg":
                case ".jpg":
                    return MediaType.IMAGE_JPEG;
                case ".png":
                    return MediaType.IMAGE_PNG;
            }
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        throw new RuntimeIOException(HttpStatus.BAD_REQUEST);
    }
}
