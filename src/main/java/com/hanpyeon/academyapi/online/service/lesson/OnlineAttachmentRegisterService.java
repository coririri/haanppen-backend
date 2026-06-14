package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachment;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachmentRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideoRepository;
import com.hanpyeon.academyapi.online.dto.RegisterOnlineVideoAttachmentCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineAttachmentRegisterService {
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineVideoAttachmentRepository onlineVideoAttachmentRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void register(final RegisterOnlineVideoAttachmentCommand command) {
        final OnlineVideo onlineVideo = loadOnlineVideoById(command.onlineVideoId());
        onlineCourseOwnerValidator.validate(command.requestMemberRole(), command.requestMemberId(), onlineVideo.getOnlineCourse().getTeacher().getId());
        
        final OnlineVideoAttachment onlineVideoAttachment = new OnlineVideoAttachment(onlineVideo, command.attachmentTitle(), command.attachmentContent());
        onlineVideoAttachmentRepository.save(onlineVideoAttachment);
    }

    private OnlineVideo loadOnlineVideoById(final Long videoId) {
        return onlineVideoRepository.loadSingleOnlineVideoWithCourseWithTeacherByVideoId(videoId)
                .orElseThrow(() -> new BusinessException("비디오를 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
