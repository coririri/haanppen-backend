package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoPort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMediaAttachmentPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.exception.MediaStoreException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterMediaAttachmentAdapter implements RegisterMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    private final MemoMediaRepository memoMediaRepository;
    private final MediaRepository mediaRepository;

    @Override
    @Transactional
    public void register(Long memoMediaId, String attachmentMediaId) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(memoMediaId)
                .orElseThrow(() -> new MemoMediaException("해당 메모를 찾을 수 없음 : " + memoMediaId, ErrorCode.MEMO_NOT_EXIST));
        final Media attachment = mediaRepository.findBySrc(attachmentMediaId)
                .orElseThrow(() -> new MemoMediaException("해당 미디어 파일을 찾을 수 없음" + attachmentMediaId, ErrorCode.NO_SUCH_MEDIA));
        final MemoMediaAttachment memoMediaAttachment = MemoMediaAttachment.of(targetMemoMedia, attachment);
        mediaAttachmentRepository.save(memoMediaAttachment);
    }
}
