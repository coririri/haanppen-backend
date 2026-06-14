package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.comment.CommentService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/board/comments")
public class CommentController {
    private final CommentService commentService;
    private final BoardMapper boardMapper;

    @Operation(summary = "댓글 등록 API", description = "질문 게시글에 댓글을 달 수 있도록 하는 API 입니다.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> addComment(
            @Valid @RequestBody CommentRegisterRequestDto commentRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        CommentRegisterDto commentRegisterDto = boardMapper.createCommentRegisterDto(commentRegisterRequestDto, memberPrincipal.memberId());
        final Long createdCommentId = commentService.addComment(commentRegisterDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdCommentId)
                        .toUri()
        ).build();
    }

    @Operation(summary = "댓글 내용 수정 API")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateComment(
            @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        final CommentUpdateDto commentUpdateDto = boardMapper.createCommentUpdateDto(commentUpdateRequestDto, memberPrincipal.memberId(), memberPrincipal.role());
        commentService.updateComment(commentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제할 수 있는 API 입니다")
    @DeleteMapping("/{commentId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {
        CommentDeleteDto commentDeleteDto = boardMapper.createCommentDeleteDto(commentId, memberPrincipal.memberId(), memberPrincipal.role());
        commentService.deleteComment(commentDeleteDto);
        return ResponseEntity.noContent().build();
    }
}
