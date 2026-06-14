package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Media;

public interface LoadMediaBySourcePort {
    Media loadMediaBySource(final String source);
}
