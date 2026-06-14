package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.DeleteAttachmentCommand;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.in.DeleteAttachmentUseCase;
import com.hanpyeon.academyapi.course.application.port.out.DeleteAttachmentPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadAttachmentOwnedMemberIdPort;
import com.hanpyeon.academyapi.course.application.port.out.ValidateSuperUserPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAttachmentService implements DeleteAttachmentUseCase {
    private final ValidateSuperUserPort validateSuperUserPort;
    private final LoadAttachmentOwnedMemberIdPort loadAttachmentOwnedMemberIdPort;
    private final DeleteAttachmentPort deleteAttachmentPort;

    @Override
    @Transactional
    public void delete(DeleteAttachmentCommand command) {
        validateAuthority(command);
        deleteAttachmentPort.delete(command.getTargetAttachmentId());
    }

    private void validateAuthority(final DeleteAttachmentCommand command) {
        if (loadAttachmentOwnedMemberIdPort.findOwnerId(command.getTargetAttachmentId()).equals(command.getRequestMemberId())) {
            return;
        }
        if (validateSuperUserPort.isSuperUser(command.getRequestMemberId())) {
            return;
        }
        throw new CourseException("첨부파일을 지울 수 있는 권한이 없습니다.", ErrorCode.CANNOT_FIND_ATTACHMENT);
    }
}
