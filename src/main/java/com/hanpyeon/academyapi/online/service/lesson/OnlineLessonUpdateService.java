package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoRepository;
import com.hanpyeon.academyapi.online.dto.OnlineLessonInitializeCommand;
import com.hanpyeon.academyapi.online.dto.OnlineVideoPreviewUpdateCommand;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.service.lesson.update.OnlineLessonUpdateManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineLessonUpdateService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineLessonUpdateManager onlineLessonInfoUpdateManager;
    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void updateLessonInfo(@Validated final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        final OnlineCourse onlineCourse = loadCourseAndCategoryByCourseId(updateOnlineLessonInfoCommand.targetCourseId());
        onlineCourseOwnerValidator.validate(updateOnlineLessonInfoCommand.requestMemberRole(), updateOnlineLessonInfoCommand.requestMemberId(), onlineCourse.getTeacher().getId());

        onlineLessonInfoUpdateManager.update(onlineCourse, updateOnlineLessonInfoCommand);
    }

    @Transactional
    public void updatePreviewStauts(@Validated final OnlineVideoPreviewUpdateCommand command) {
        final OnlineVideo onlineVideo = loadVideoAndVideosByCourseId(command.onlineVideoId());
        onlineCourseOwnerValidator.validate(command.requetMemberRole(), command.requestMemerId(), onlineVideo.getOnlineCourse().getTeacher().getId());

        onlineVideo.setPreviewStatus(command.previewStatus());
    }

    @Transactional
    public void initializeCourse(@Validated final OnlineLessonInitializeCommand command) {
        final OnlineCourse onlineCourse = loadCourseAndVideos(command.onlineCourseId());
        onlineCourseOwnerValidator.validate(command.requetMemberROle(), command.requetMemberId(), onlineCourse.getTeacher().getId());

        onlineCourse.clearContents();
        onlineCourseRepository.removeAllOnlineVideoAttachmentsIn(onlineCourse.getVideos().stream()
                .map(OnlineVideo::getId)
                .toList());
        onlineCourseRepository.removeOnlineCourseVideos(onlineCourse.getId());
    }

    private OnlineCourse loadCourseAndCategoryByCourseId(final Long courseId) {
        return onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId)
                .orElseThrow(() -> new BusinessException(courseId + " : 반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineCourse loadCourseAndVideos(final Long courseId) {
        return onlineCourseRepository.loadOnlineCourseAndVideosByCourseId(courseId)
                .orElseThrow(() -> new BusinessException(courseId + " : 반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineVideo loadVideoAndVideosByCourseId(final Long videoId) {
        return onlineVideoRepository.loadSingleOnlineVideoWithCourseWithTeacherByVideoId(videoId)
                .orElseThrow(() -> new BusinessException(videoId + " : 등록된 영상을 찾을 수 없습니다 : ", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

}
