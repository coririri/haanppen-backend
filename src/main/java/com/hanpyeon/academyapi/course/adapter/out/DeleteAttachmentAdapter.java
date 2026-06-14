package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.DeleteAttachmentPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeleteAttachmentAdapter implements DeleteAttachmentPort {

    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Override
    public void delete(Long attachmentId) {
        if (Objects.isNull(attachmentId)) {
            throw new MemoMediaException("잘못된 api 호출 : " + attachmentId, ErrorCode.CANNOT_FIND_ATTACHMENT);
        }
        mediaAttachmentRepository.deleteById(attachmentId);
    }
}
