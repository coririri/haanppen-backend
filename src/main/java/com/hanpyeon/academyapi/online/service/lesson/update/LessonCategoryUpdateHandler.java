package com.hanpyeon.academyapi.online.service.lesson.update;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCategory;
import com.hanpyeon.academyapi.online.dao.OnlineCategoryRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LessonCategoryUpdateHandler implements LessonUpdateHandler {
    private final OnlineCategoryRepository onlineCategoryRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (Objects.isNull(updateOnlineLessonInfoCommand.categoryId())) {
            return;
        }
        final OnlineCategory onlineCategory = loadSingleCategory(updateOnlineLessonInfoCommand.categoryId());
        onlineCourse.setOnlineCategory(onlineCategory);
    }

    private OnlineCategory loadSingleCategory(final Long onlineCategoryId) {
        return onlineCategoryRepository.findById(onlineCategoryId)
                .orElseThrow(() -> new BusinessException("일치하는 카테고리를 찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
