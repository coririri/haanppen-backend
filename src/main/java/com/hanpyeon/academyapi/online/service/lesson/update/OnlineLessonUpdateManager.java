package com.hanpyeon.academyapi.online.service.lesson.update;

import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OnlineLessonUpdateManager {
    private final List<LessonUpdateHandler> lessonUpdateHandlers;

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(
            final OnlineCourse onlineCourse,
            final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand
    ) {
        lessonUpdateHandlers.forEach(lessonInfoUpdateHandler -> lessonInfoUpdateHandler.update(onlineCourse, updateOnlineLessonInfoCommand));
    }
}
