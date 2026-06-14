package com.hanpyeon.academyapi.media.validator;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface UploadFileValidator {
    boolean validate(UploadFile uploadFile);
}
