package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.dto.DownloadCommand;
import com.hanpyeon.academyapi.media.dto.DownloadResult;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.exception.MediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadService {

    private final MediaStorage mediaStorage;
    private final MediaRepository mediaRepository;

    public DownloadResult getResource(final DownloadCommand downloadCommand) {
        final MediaDto mediaDto = mediaStorage.loadFile(downloadCommand.getFileName());
        final String fileName = mediaRepository.findFileNameBySrc(downloadCommand.getFileName())
                .orElseThrow(() -> new MediaException("해당 파일의 이름 찾기 불가능", ErrorCode.NO_SUCH_MEDIA));
        log.info("FILENAME = " + fileName);
        return new DownloadResult(mediaDto.data(), fileName);
    }
}
