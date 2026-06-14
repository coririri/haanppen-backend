package com.hanpyeon.academyapi.media.service;

import java.io.InputStream;

public interface UploadFile {
    String getUniqueFileName();
    InputStream getInputStream();
    String getExtension();
}
