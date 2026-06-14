package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.AttachmentUploadChunkFile;
import com.hanpyeon.academyapi.course.application.dto.ChunkProcessedResult;
import com.hanpyeon.academyapi.course.application.port.out.ChunkFileHandlerPort;
import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.upload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkFileStoreAdapter implements ChunkFileHandlerPort {

    @Value("${memo.attachment.directory.path}")
    private String attachmentFileDirectoryPath;
    private final UploadService uploadService;


    @Override
    public ChunkProcessedResult process(AttachmentUploadChunkFile chunkFile) {
        final UploadMediaDto uploadMediaDto = create(chunkFile);
        final ChunkStoreResult result = uploadService.upload(uploadMediaDto);
        return ChunkProcessedResult.of(result);
    }

    private UploadMediaDto create(final AttachmentUploadChunkFile chunkFile) {
        return new UploadMediaDto(
                chunkFile.getFile(),
                chunkFile.getFileName(),
                chunkFile.getTotalChunkCount(),
                chunkFile.getCurrChunkIndex(),
                chunkFile.getIsLast(),
                chunkFile.getRequestMemberId(),
                this.attachmentFileDirectoryPath,
                chunkFile.getExtension(),
                null
        );
    }
}
