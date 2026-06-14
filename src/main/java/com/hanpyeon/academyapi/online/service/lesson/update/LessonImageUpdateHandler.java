package com.hanpyeon.academyapi.online.service.lesson.update;

import com.hanpyeon.academyapi.board.service.comment.CommentService;
import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.repository.ImageRepository;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonImageUpdateHandler implements LessonUpdateHandler{
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final CommentService commentService;

    @Override
    public void update(OnlineCourse onlineCourse, UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (updateOnlineLessonInfoCommand.imageSrc() == null) {
            return;
        }
        final Image newImage = loadImage(updateOnlineLessonInfoCommand.imageSrc());
        onlineCourse.setImage(newImage);
    }

    private Image loadImage(final String imageSrc) {
        return imageRepository.findBySrc(imageSrc)
                .orElseThrow(() -> new BusinessException(imageSrc + " : 찾을 수 없는 사진", ErrorCode.NO_SUCH_MEDIA));
    }
}
