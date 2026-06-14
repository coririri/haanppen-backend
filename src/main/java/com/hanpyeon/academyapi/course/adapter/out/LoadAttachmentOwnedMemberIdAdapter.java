package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadAttachmentOwnedMemberIdPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoadAttachmentOwnedMemberIdAdapter implements LoadAttachmentOwnedMemberIdPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    @Override
    @Transactional(readOnly = true)
    public Long findOwnerId(Long attachmentId) {
        final Member ownerMember = mediaAttachmentRepository.findCourseOwnerMemberOnThisMemoAttachment(attachmentId)
                .orElseThrow(() -> new MemoMediaException("해당 코스의 소유자를 찾을 수 없음", ErrorCode.CANNOT_FIND_ATTACHMENT));
        return ownerMember.getId();
    }
}
