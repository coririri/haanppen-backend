package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.exception.InvalidUploadFileException;
import com.hanpyeon.academyapi.media.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImageUploadFile implements UploadFile {
    private final MultipartFile multipartFile;
    private final String newFileName;
    private final String extension;

    public ImageUploadFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        this.newFileName = getNewFileName(multipartFile.getOriginalFilename());
        this.extension = parseExtension(multipartFile.getOriginalFilename());
    }

    private String getNewFileName(String fileName) {
        return UUID.randomUUID() + parseExtension(fileName);
    }

    private String parseExtension(String fileName) {
        final int extensionIdx = fileName.lastIndexOf(".");
        if (extensionIdx == -1) {
            throw new InvalidUploadFileException(ErrorCode.INVALID_UPLOAD_FILE);
        }
        return fileName.substring(extensionIdx);
    }

    @Override
    public String getUniqueFileName() {
        return this.newFileName;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new StorageException(ErrorCode.NO_SUCH_MEDIA);
        }
    }

    @Override
    public String getExtension() {
        return this.extension;
    }
}
