package com.hanpyeon.academyapi.media.controller;

import com.hanpyeon.academyapi.media.dto.DownloadCommand;
import com.hanpyeon.academyapi.media.dto.DownloadResult;
import com.hanpyeon.academyapi.media.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final DownloadService downloadService;
    @GetMapping("/api/file/download")
    public ResponseEntity<?> downloadFile(
            @RequestParam final String fileSrc
    ) {
        final DownloadResult downloadResult = downloadService.getResource(new DownloadCommand(fileSrc));
        final HttpHeaders headers = setDownloadAttachmentFileName(downloadResult.getFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(downloadResult.getInputStream()));
    }
    private HttpHeaders setDownloadAttachmentFileName(final String fileName) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + "filename=" +"\"" + fileName + "\"");
        return headers;
    }
}
