package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.QuestionValidationException;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionImageValidatorTest {

    @Mock
    ImageService imageService;

    @InjectMocks
    QuestionImageValidator questionImageValidator;

    @Test
    void 이미지_갯수_초과_에러처리_테스트() {
        List<Image> images = List.of(Mockito.mock(Image.class), Mockito.mock(Image.class), Mockito.mock(Image.class),Mockito.mock(Image.class));
        final Question question = Question.builder().images(images).build();

        assertThatThrownBy(() -> questionImageValidator.validate(question))
                .isInstanceOf(QuestionValidationException.class);
    }

    @Test
    void 이미지수_경계_테스트() {
        List<Image> images = List.of(Mockito.mock(Image.class));
        // 이미지 한개만 가능
        final Question question = Question.builder().images(images).build();

        assertDoesNotThrow(() -> questionImageValidator.validate(question));
    }
}