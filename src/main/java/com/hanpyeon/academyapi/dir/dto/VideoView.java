package com.hanpyeon.academyapi.dir.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VideoView extends FileView {
    private final Long runtimeDuration;
    private final Long fileSize;

    public VideoView(String fileName, Boolean isDir, String path, LocalDateTime createdTime, Boolean canViewByEveryone,
                     Boolean canModifyByEveryone, Long runtimeDuration, Long fileSize) {
        super(fileName, isDir, path, createdTime, canViewByEveryone, canModifyByEveryone);
        this.runtimeDuration = runtimeDuration;
        this.fileSize = fileSize;
    }
}
