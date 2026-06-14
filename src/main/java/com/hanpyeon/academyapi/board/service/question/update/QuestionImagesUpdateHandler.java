package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class QuestionImagesUpdateHandler extends QuestionUpdateHandler {
    private final ImageService imageService;
    @Override
    boolean applicable(QuestionUpdateDto questionUpdateDto) {
        if (Objects.isNull(questionUpdateDto.imageSources())) {
            return false;
        }
        return true;
    }

    @Override
    void process(Question question, QuestionUpdateDto questionUpdateDto) {
        final List<Image> images = imageService.loadImages(questionUpdateDto.imageSources());
        question.changeImages(images);
    }
}
