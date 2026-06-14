package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.AddOnlineCourseVideoRequest;
import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineCourseVideoCommand;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineVideoAttachmentCommand;
import com.hanpyeon.academyapi.online.dto.OnlineLessonInitializeCommand;
import com.hanpyeon.academyapi.online.dto.OnlineVideoPreviewUpdateCommand;
import com.hanpyeon.academyapi.online.dto.OnlineVideoPreviewUpdateRequest;
import com.hanpyeon.academyapi.online.dto.RegisterOnlineVideoAttachmentCommand;
import com.hanpyeon.academyapi.online.dto.RegisterOnlineVideoAttachmentRequest;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoRequest;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineVideoSequenceRequest;
import com.hanpyeon.academyapi.online.service.lesson.OnlineAttachmentDeleteService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineAttachmentRegisterService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineLessonUpdateService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineVideoDeleteService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineVideoRegisterService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineVideoSequenceUpdateService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.MemoryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.PUT;

@RestController
@RequestMapping("/api/online-courses/lesson")
@RequiredArgsConstructor
@Tag(name = "온라인 수업")
@SecurityRequirement(name = "jwtAuth")
class OnlineLessonController {
    private final OnlineLessonUpdateService onlineLessonUpdateService;
    private final OnlineVideoDeleteService onlineVideoDeleteService;
    private final OnlineVideoRegisterService onlineVideoRegisterService;
    private final OnlineVideoSequenceUpdateService onlineVideoSequenceUpdateService;
    private final OnlineAttachmentRegisterService onlineAttachmentRegisterService;
    private final OnlineAttachmentDeleteService onlineAttachmentDeleteService;

    @PutMapping
    @Operation(summary = "온라인 수업의 대표 정보를 수정하는 API 입니다", description = "필드를 null로 보내면, 해당 필드는 수정하지 않습니다.")
    public ResponseEntity<?> updateOnlineLessonInfo(
            @Validated @RequestBody final UpdateOnlineLessonInfoRequest updateOnlineLessonInfoRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand = updateOnlineLessonInfoRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        onlineLessonUpdateService.updateLessonInfo(updateOnlineLessonInfoCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{lessonId}")
    @Operation(summary = "온라인 수업의 내용을 초기화 API", description = "등록된 영상 및 첨부파일, 수업 제목, 수업 범위, 수업 내용 삭제")
    public ResponseEntity<?> initializeLesson(
            @PathVariable(required = true) final Long lessonId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final OnlineLessonInitializeCommand command = new OnlineLessonInitializeCommand(lessonId, memberPrincipal.memberId(), memberPrincipal.role());
        onlineLessonUpdateService.initializeCourse(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/videos/sequence")
    @Operation(summary = "온라인 수업 영상 순서 수정 API")
    public ResponseEntity<?> changeSequence(
            @Validated @RequestBody final UpdateOnlineVideoSequenceRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateOnlineVideoSequenceCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineVideoSequenceUpdateService.updateSequence(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/videos")
    @Operation(summary = "온라인 수업에 영상 등록 API")
    public ResponseEntity<?> updateOnlineCourseVideos(
            @Validated @RequestBody final AddOnlineCourseVideoRequest addOnlineCourseVideosRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineVideoCommand addOnlineVideoCommand = addOnlineCourseVideosRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role());
        onlineVideoRegisterService.addOnlineVideo(addOnlineVideoCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/videos")
    @Operation(summary = "온라인 수업 영상 Preview 옵션 수정 API")
    public ResponseEntity<?> updateOnlineVideoPreviewStatus(
            @Valid @RequestBody final OnlineVideoPreviewUpdateRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final OnlineVideoPreviewUpdateCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineLessonUpdateService.updatePreviewStauts(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{onlineCourseId}/videos/{onlineVideoId}")
    @Operation(summary = "온라인 수업 영상 삭제 API", description = "온라인 수업 영상을 삭제, 첨부자료까지 모두 삭제")
    public ResponseEntity<?> deleteOnlineCourseVideo(
            @PathVariable final Long onlineCourseId,
            @PathVariable final Long onlineVideoId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineCourseVideoCommand deleteOnlineCourseVideoCommand = new DeleteOnlineCourseVideoCommand(
                onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineVideoDeleteService.deleteOnlineVideo(deleteOnlineCourseVideoCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{onlineCourseId}/videos/{onlineVideoId}/attachments")
    @Operation(summary = "온라인 영상 첨부자료 추가 API", description = "첨부자료 추가")
    public ResponseEntity<?> registerAttachment(
            @PathVariable(required = true) final Long onlineCourseId,
            @PathVariable(required = true) final Long onlineVideoId,
            @Validated @RequestBody final RegisterOnlineVideoAttachmentRequest registerOnlineVideoAttachmentRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final RegisterOnlineVideoAttachmentCommand command = registerOnlineVideoAttachmentRequest.toCommand(onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineAttachmentRegisterService.register(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{onlineCourseId}/videos/{onlineVideoId}/attachments/{attachmentId}")
    @Operation(summary = "온라인 영상 첨부자료 삭제 API")
    public ResponseEntity<?> deleteAttachment(
            @PathVariable(required = true) final Long onlineCourseId,
            @PathVariable(required = true) final Long onlineVideoId,
            @PathVariable(required = true) final Long attachmentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineVideoAttachmentCommand command = new DeleteOnlineVideoAttachmentCommand(attachmentId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineAttachmentDeleteService.deleteAttachment(command);
        return ResponseEntity.noContent().build();
    }
}
