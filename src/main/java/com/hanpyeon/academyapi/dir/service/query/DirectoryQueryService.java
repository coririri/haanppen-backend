package com.hanpyeon.academyapi.dir.service.query;

import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;

import java.util.List;

public interface DirectoryQueryService {
    List<FileView> queryDirectory(final QueryDirectoryDto queryDirectoryDto);
}
