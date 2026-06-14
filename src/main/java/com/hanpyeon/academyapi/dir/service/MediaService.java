package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.DeleteMediaDto;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.delete.DeleteService;
import com.hanpyeon.academyapi.dir.service.media.upload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final UploadService uploadService;
    private final DeleteService deleteService;

    public ChunkStoreResult uploadChunk(final UploadMediaDto uploadMediaDto) {
        return uploadService.upload(uploadMediaDto);
    }

    public void deleteMedia(final DeleteMediaDto deleteMediaDto) {
        deleteService.delete(deleteMediaDto);
    }
}
