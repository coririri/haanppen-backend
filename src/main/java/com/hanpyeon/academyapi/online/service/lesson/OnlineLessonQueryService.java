package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCategory;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineStudent;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachment;
import com.hanpyeon.academyapi.online.dto.LessonCategoryInfo;
import com.hanpyeon.academyapi.online.dto.OnlineLessonDetail;
import com.hanpyeon.academyapi.online.dto.OnlineLessonQueryCommand;
import com.hanpyeon.academyapi.online.dto.OnlineVideoAttachmentDetail;
import com.hanpyeon.academyapi.online.dto.OnlineVideoDetail;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineLessonQueryService {
    private final OnlineCourseRepository onlineCourseRepository;

    public OnlineLessonDetail loadDetails(@Validated final OnlineLessonQueryCommand command) {
        final OnlineCourse onlineCourses = onlineCourseRepository.loadOnlineCourseAndVideosAndCategoryById(
                        command.courseId())
                .orElseThrow(() -> new BusinessException(command.courseId() + "를 찾을 수 없음",
                        ErrorCode.ONLINE_COURSE_EXCEPTION));
        return mapToDetail(onlineCourses, command.requestMemberId(), command.requestMemberRole());
    }

    private OnlineLessonDetail mapToDetail(final OnlineCourse onlineCourse, final Long requestMemberId, final Role role) {
        final boolean isIncludedStudent = checkViewable(role, requestMemberId, onlineCourse.getOnlineStudents());
        return new OnlineLessonDetail(
                onlineCourse.getId(),
                onlineCourse.getCourseTitle(),
                onlineCourse.getCourseRange(),
                onlineCourse.getCourseContent(),
                onlineCourse.getVideos().stream()
                        .map(onlineVideo -> this.mapToDetail(onlineVideo, isIncludedStudent))
                        .collect(Collectors.toList()),
                toCategory(onlineCourse.getOnlineCategory()),
                onlineCourse.getImage() == null ? null : onlineCourse.getImage().getSrc()
        );
    }

    private boolean checkViewable(final Role role, final Long requestMemberId, final List<OnlineStudent> studentIds) {
        if (role.equals(Role.STUDENT)) {
            return studentIds.stream()
                    .map(onlineStudent -> onlineStudent.getMember().getId())
                    .anyMatch(studentId -> studentId.equals(requestMemberId));
        }
        // 학생이 아닌 조회에선 모두 true
        return true;
    }

    private LessonCategoryInfo toCategory(final OnlineCategory onlineCategory) {
        if (Objects.isNull(onlineCategory)) {
            return null;
        }
        return new LessonCategoryInfo(onlineCategory.getId(), onlineCategory.getParentCategory().getCategoryName(),
                onlineCategory.getCategoryName());
    }

    private OnlineVideoDetail mapToDetail(final OnlineVideo onlineVideo, final boolean isIncludedMember) {
        if (onlineVideo.getPreview() || isIncludedMember) {
            return new OnlineVideoDetail(
                    onlineVideo.getId(),
                    onlineVideo.getVideoSequence(),
                    onlineVideo.getVideoName(),
                    onlineVideo.getPreview(),
                    onlineVideo.getMedia().getSrc(),
                    onlineVideo.getMedia().getDuration(),
                    onlineVideo.getVideoAttachments().stream()
                            .map(this::mapToDetail)
                            .toList()
            );
        }
        return new OnlineVideoDetail(
                onlineVideo.getId(),
                onlineVideo.getVideoSequence(),
                onlineVideo.getVideoName(),
                onlineVideo.getPreview(),
                null,
                null,
                null
        );
    }

    private OnlineVideoAttachmentDetail mapToDetail(final OnlineVideoAttachment onlineVideoAttachment) {
        return new OnlineVideoAttachmentDetail(onlineVideoAttachment.getId(),
                onlineVideoAttachment.getTitle(),
                onlineVideoAttachment.getContent());
    }
}
