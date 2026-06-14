package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoRepository;
import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineVideoRegisterService {
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineCourseRepository onlineCourseRepository;
    private final MediaRepository mediaRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    /**
     * @param addVideoToOnlineCourseCommand 해당 온라인 강의와 관련된 데이터를 모두 삭제 후, 업데이트 실시
     */
    @Transactional
    public void addOnlineVideo(@Validated final AddOnlineVideoCommand addVideoToOnlineCourseCommand) {
        final OnlineCourse onlineCourse = loadOnlineCourseWithVideos(addVideoToOnlineCourseCommand.onlineCourseId());
        onlineCourseOwnerValidator.validate(addVideoToOnlineCourseCommand.requestMemberRole(), addVideoToOnlineCourseCommand.requestMemberId(), onlineCourse.getTeacher().getId());

        final Media media = loadMedia(addVideoToOnlineCourseCommand.onlineVideoCommand().videoSrc());
        final Integer matchSequence = getMatchSequence(onlineCourse);
        final OnlineVideo onlineVideo = new OnlineVideo(onlineCourse, media, media.getMediaName(),
                addVideoToOnlineCourseCommand.onlineVideoCommand().isPreview(), matchSequence);

        onlineVideoRepository.save(onlineVideo);
    }

    private Integer getMatchSequence(final OnlineCourse onlineCourse) {
        final Integer newestSequence = onlineCourse.getVideos().stream()
                .map(OnlineVideo::getVideoSequence)
                .max((o1, o2) -> o1 - o2)
                .orElseGet(() -> 0);
        return newestSequence + 1;
    }

    private OnlineCourse loadOnlineCourseWithVideos(final Long onlineCourseId) {
        return onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourseId)
                .orElseThrow(() -> new BusinessException("온라인 수업을 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private Media loadMedia(final String mediaSource) {
        return mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new BusinessException("미디어를 찾을 수 없음 : " + mediaSource,
                        ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
