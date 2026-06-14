package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.QuestionValidationException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class QuestionImageValidator implements QuestionValidator {
    private final ImageService imageService;
    @Override
    public void validate(Question question) {
        if (question.getImages().size() > 3) {
            imageService.removeImage(question.getImages());
            throw new QuestionValidationException("게시글에 들어갈 수 있는 이미지 갯수는 최대 3개", ErrorCode.QUESTION_IMAGE_OVER_LENGTH);
        }
    }
}
