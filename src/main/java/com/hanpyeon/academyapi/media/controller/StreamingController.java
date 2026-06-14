package com.hanpyeon.academyapi.media.controller;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.dto.HttpStreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingResult;
import com.hanpyeon.academyapi.media.exception.MediaException;
import com.hanpyeon.academyapi.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StreamingController {

    private final MediaService mediaService;

    @GetMapping("/api/media/stream")
    public ResponseEntity<?> streamMedia(
            @RequestParam final String resourceId,
            @RequestHeader final HttpHeaders requestHeaders
    ) throws IOException {
        final StreamingCommand command = createCommand(requestHeaders, resourceId);
        final StreamingResult result = mediaService.stream(command);
        log.debug("시작   인덱스 : " + (result.getStartIndex()));
        log.debug("마지막 인덱스 : " + (result.getEndIndex()));

        final HttpHeaders headers = setHeadersWithResult(result);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(result.getInputStream().readAllBytes());
    }

    private HttpHeaders setHeadersWithResult(final StreamingResult result) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + result.getStartIndex() + '-' + result.getEndIndex() + "/" + result.getTotalFileSize());
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(result.getCurrentSize()));
        headers.setContentType(result.getMediaType());
        return headers;
    }

    StreamingCommand createCommand(final HttpHeaders headers, final String fileName) {
        final List<HttpRange> ranges = headers.getRange();
        log.debug(ranges.toString());
        if (ranges.isEmpty() || ranges.size() > 1) {
            // 에러코드 수정 필요
            throw new MediaException("Range 헤더 오류 ", ErrorCode.MEDIA_ACCESS_EXCEPTION);
        }
        final HttpRange range = ranges.get(0);
        return new HttpStreamingCommand(range, fileName);
    }
}
