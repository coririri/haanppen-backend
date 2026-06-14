package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.exception.NoSuchMediaException;
import com.hanpyeon.academyapi.media.exception.NotSupportedMediaException;
import com.hanpyeon.academyapi.media.exception.StorageException;
import com.hanpyeon.academyapi.media.service.UploadFile;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
@Primary
public class LocalStorage implements MediaStorage {

    protected final String storagePath;

    public LocalStorage(@Value("${server.local.storage}") String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public String store(final UploadFile uploadFile) {
        final Path path = resolveFilePath(uploadFile.getUniqueFileName());
        try (final OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW)) {
            uploadFile.getInputStream().transferTo(outputStream);
        } catch (IOException e) {
            throw new StorageException("서버 상에서 오류가 발생했습니다. 다시 시도해주세요" + e, ErrorCode.MEDIA_STORE_EXCEPTION);
        }
        return uploadFile.getUniqueFileName();
    }

    @Override
    public void remove(String fileName) {
        final Path absoluteMediaPath = this.resolveFilePath(fileName);
        absoluteMediaPath.toFile().delete();
    }

    @Override
    public MediaDto loadFile(final String fileName) {
        final Path absoluteImagePath = this.resolveFilePath(fileName);
        Resource fileResource = new FileSystemResource(absoluteImagePath);
        if (!fileResource.exists()) {
            throw new NoSuchMediaException(ErrorCode.NO_SUCH_MEDIA);
        }
        try {
            return new MediaDto(fileResource.getInputStream(), MediaType.parseMediaType(Files.probeContentType(fileResource.getFile().toPath())), fileResource.contentLength());
        } catch (IOException | InvalidMediaTypeException | InvalidPathException | SecurityException e) {
            throw new NotSupportedMediaException("지원하지 않는 이미지 입니다.", ErrorCode.NOT_SUPPORTED_MEDIA);
        }
    }

    @Override
    public Set<String> loadAllFileNames() {
        final Path storagePath = resolveFilePath("");
        try {
            return Files.walk(storagePath)
                    .filter(Files::isRegularFile)
                    .map(path -> path.toFile().getName())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected final Path resolveFilePath(final String fileName) {
        return Paths.get(storagePath)
                .toAbsolutePath()
                .normalize()
                .resolve(fileName);
    }
}
