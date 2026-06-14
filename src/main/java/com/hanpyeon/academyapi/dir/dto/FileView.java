package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class FileView {
    private final String fileName;
    private final Boolean isDir;
    private final String path;
    private final LocalDateTime createdTime;
    private final Boolean canViewByEveryone;
    private final Boolean canModifyByEveryone;
}
