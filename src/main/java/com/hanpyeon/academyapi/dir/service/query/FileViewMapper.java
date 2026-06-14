package com.hanpyeon.academyapi.dir.service.query;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.media.entity.Media;

interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final Media media);
}
