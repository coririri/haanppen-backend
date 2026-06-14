package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.aspect.log.ErrorLoggable;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.service.UploadFile;

import java.util.List;
import java.util.Set;

/**
 * 외부 저장소에 이미지, 영상 데이터를 저장하고 가져오는 역할을 수행합니다.
 */
@ErrorLoggable
public interface MediaStorage {
    String store(UploadFile uploadFile);
    void remove(String fileName);

    MediaDto loadFile(String fileName);

    Set<String> loadAllFileNames();
}
