package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Media;

import java.util.List;

public interface LoadMediasPort {
    List<Media> loadMedias(final List<String> mediaIds);
}
