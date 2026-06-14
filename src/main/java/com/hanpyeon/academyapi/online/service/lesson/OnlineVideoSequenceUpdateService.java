package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineVideoSequenceCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineVideoSequenceUpdateService {
    private final OnlineCourseRepository onlineCourseRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void updateSequence(@Validated final UpdateOnlineVideoSequenceCommand command) {
        final OnlineCourse onlineCourse = loadOnlineCourse(command.targetCourseId());
        onlineCourseOwnerValidator.validate(command.requetMemberRole(), command.requestMemberId(), onlineCourse.getTeacher().getId());

        final OnlineVideo baseVideo = findById(onlineCourse.getVideos(), command.targetVideoId());
        final OnlineVideo targetVideo = findSequenceVideo(onlineCourse.getVideos(), command.updatedSequence());
        update(targetVideo, baseVideo);
    }

    private void update(final OnlineVideo onlineVideo, final OnlineVideo targetVideo) {
        onlineVideo.changeSequence(targetVideo);
    }

    private OnlineVideo findSequenceVideo(final List<OnlineVideo> onlineVideos, final Integer targetSequence) {
        return onlineVideos.stream()
                .filter(onlineVideo -> onlineVideo.getVideoSequence().equals(targetSequence))
                .findAny()
                .orElseThrow(() -> new BusinessException("등록된 순서 번호 [" + targetSequence + "] 를  찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));

    }

    private OnlineVideo findById(final List<OnlineVideo> onlineVideos, final Long videoId) {
        return onlineVideos.stream()
                .filter(onlineVideo -> onlineVideo.getId().equals(videoId))
                .findAny()
                .orElseThrow(() -> new BusinessException("등록된 videoID [" + videoId + "] 를  찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineCourse loadOnlineCourse(final Long onlineCourseId) {
        return onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourseId)
                .orElseThrow(() -> new BusinessException("해당 반을 찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
