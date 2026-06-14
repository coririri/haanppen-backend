package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.dao.CommentRepository;
import com.hanpyeon.academyapi.board.service.comment.content.CommentContentManager;
import com.hanpyeon.academyapi.board.service.comment.register.CommentRegisterManager;
import com.hanpyeon.academyapi.media.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    CommentRegisterManager commentRegisterManager;
    @Mock
    CommentContentManager commentContentManager;
    @Mock
    ImageService imageService;

    CommentService commentService;

    @BeforeEach
    void init() {
        this.commentService = new CommentService(commentRepository, commentRegisterManager, commentContentManager, imageService);
    }

    @Test
    void 질문_추가_테스트() {
        CommentRegisterDto commentRegisterDto = Mockito.mock(CommentRegisterDto.class);
        Comment comment = Mockito.mock(Comment.class);
        Mockito.when(commentRegisterManager.register(commentRegisterDto))
                .thenReturn(comment);

        assertThat(commentService.addComment(commentRegisterDto))
                .isEqualTo(comment.getId());
    }
}