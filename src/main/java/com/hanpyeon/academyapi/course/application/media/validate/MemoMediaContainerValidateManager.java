package com.hanpyeon.academyapi.course.application.media.validate;

import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoMediaContainerValidateManager {
    private final List<MemoMediaContainerValidator> validators;

    public void validate(final MemoMediaContainer container) {
        validators.stream()
                .forEach(validator -> validator.validate(container));
    }
}
