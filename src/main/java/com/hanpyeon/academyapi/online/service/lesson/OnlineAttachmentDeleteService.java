package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachment;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachmentRepository;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineVideoAttachmentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class OnlineAttachmentDeleteService {
    private final OnlineVideoAttachmentRepository onlineVideoAttachmentRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void deleteAttachment(@Validated final DeleteOnlineVideoAttachmentCommand command) {
        final OnlineVideoAttachment onlineVideoAttachment = onlineVideoAttachmentRepository.findWithOnlineCourseAndVideosById(command.attachmentId())
                .orElseThrow(() -> new BusinessException("첨부파일을 찾지 못했습니다." + command.attachmentId(), ErrorCode.ONLINE_COURSE_EXCEPTION));
        onlineCourseOwnerValidator.validate(command.requestMemberRole(), command.requestMemberId(), onlineVideoAttachment.getOnlineVideo().getOnlineCourse().getTeacher().getId());
        onlineVideoAttachmentRepository.delete(onlineVideoAttachment);
    }
}
