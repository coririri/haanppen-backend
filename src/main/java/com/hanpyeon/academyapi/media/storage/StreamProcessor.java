package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.media.dto.ChunkedMediaDto;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Service
@Slf4j
public class StreamProcessor {
    private static final Long MAXIMUM_CHUNK_SIZE = 1024 * 1024L;
    private static final Integer BUFFER_SIZE = 1024;

    public ChunkedMediaDto getProcessedInputStream(final StreamingCommand command, final MediaDto mediaDto) {
        final InputStream inputStream = mediaDto.data();
        final Long startIndex = getStartIndex(command, mediaDto.fileSize());
        final Long endIndex = getEndIndex(command, mediaDto.fileSize(), startIndex);
        try {
            inputStream.skip(startIndex);
            final AssembledStreamInfo info = sliceInputStream(inputStream, endIndex - startIndex + 1);
            return new ChunkedMediaDto(info.inputStream, info.totalByteSize, startIndex, startIndex + info.totalByteSize - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AssembledStreamInfo sliceInputStream(final InputStream inputStream, long readSize) throws IOException {
        log.debug("목표 청크 량 : " + readSize);
        final List<InputStream> inputStreams = new ArrayList<>();
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        long totalReadByteCount = 0;
        while (true) {
            final byte[] buffer = new byte[BUFFER_SIZE];
            final int currReadLength = bufferedInputStream.read(buffer);
            if (currReadLength == -1) break;

            readSize -= currReadLength;
            if (readSize < 0) {
                log.debug("#### 초과된 양 : " + readSize);
                inputStreams.add(new ByteArrayInputStream(buffer, 0, (int) (currReadLength + readSize)));
                totalReadByteCount += (int) (currReadLength + readSize);
                log.debug("현재 저장한 양 ( 마지막이네? ) : " + (int) (currReadLength - readSize));
                break;
            }
            inputStreams.add(new ByteArrayInputStream(buffer, 0, currReadLength));
            log.debug("현재 저장한 양 : " + currReadLength);
            totalReadByteCount += currReadLength;
            if (readSize == 0) {
                break;
            }
        }
        log.debug("실제로 읽은 양 : " + totalReadByteCount);
        final Enumeration<InputStream> enumeratedInputStreams = Collections.enumeration(inputStreams);
        return new AssembledStreamInfo(totalReadByteCount, new SequenceInputStream(enumeratedInputStreams));
    }

    private Long getStartIndex(final StreamingCommand command, final Long fileRealSize) {
        return command.getStartRangeIndex(fileRealSize);
    }

    private Long getEndIndex(final StreamingCommand command, final Long fileRealSize, final Long startIndex) {
        final Long requestEndIndex = command.getLastRangeIndex(fileRealSize);

        if (requestEndIndex - startIndex > MAXIMUM_CHUNK_SIZE) {
            log.debug("최대값 조정 마지막 인덱스 : " + startIndex + requestEndIndex);
            return startIndex + MAXIMUM_CHUNK_SIZE;
        }
        log.debug("조장안됨 마지막 인덱스 : " + requestEndIndex);
        return requestEndIndex;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    private static class AssembledStreamInfo {
        private final Long totalByteSize;
        private final InputStream inputStream;
    }
}
