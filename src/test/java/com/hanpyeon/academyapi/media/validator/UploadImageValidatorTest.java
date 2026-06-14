package com.hanpyeon.academyapi.media.validator;

import com.hanpyeon.academyapi.media.service.ImageUploadFile;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class UploadImageValidatorTest {
//    @Test
    void 파일_유효성_성공_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "heejong.png",
                ContentType.IMAGE_PNG.toString(),
                "image".getBytes());
        UploadFileExtensionValidator uploadImageValidator = new UploadFileExtensionValidator();

        assertThat(uploadImageValidator.validate(new ImageUploadFile(multipartFile)))
                .isTrue();
    }
//    @Test
    // 유효성 검사 로직 정해지지 않음
    void 파일_유효성_실패_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "heejong.png",
                ContentType.IMAGE_PNG.toString(),
                (byte[]) null);
        UploadFileExtensionValidator uploadImageValidator = new UploadFileExtensionValidator();

        assertThat(uploadImageValidator.validate(new ImageUploadFile(multipartFile)))
                .isFalse();
    }
}