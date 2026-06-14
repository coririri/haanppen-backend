package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface MergedUploadFile extends UploadFile {
    boolean completed();
    Long getDuration();
    Long getSize();
}
