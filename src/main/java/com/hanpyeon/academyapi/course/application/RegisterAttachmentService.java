package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.application.dto.AttachmentUploadChunkFile;
import com.hanpyeon.academyapi.course.application.dto.ChunkProcessedResult;
import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentChunkCommand;
import com.hanpyeon.academyapi.course.application.port.in.RegisterAttachmentUseCase;
import com.hanpyeon.academyapi.course.application.port.out.ChunkFileHandlerPort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMediaAttachmentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterAttachmentService implements RegisterAttachmentUseCase {

    private final ChunkFileHandlerPort chunkFileTransferPort;
    private final RegisterMediaAttachmentPort registerMediaAttachmentPort;

    @Override
    public AttachmentChunkResult register(RegisterAttachmentChunkCommand registerAttachmentCommand) {
        final AttachmentUploadChunkFile uploadChunkFile = AttachmentUploadChunkFile.of(registerAttachmentCommand);
        final ChunkProcessedResult result = chunkFileTransferPort.process(uploadChunkFile);
        log.debug(result.toString());
        if (result.getIsCompleted()) {
            // 데이터베이스 업로드 로직
            registerMediaAttachmentPort.register(registerAttachmentCommand.memoMediaId(), result.getStoredName());
        }
        return AttachmentChunkResult.of(result);
    }
}
