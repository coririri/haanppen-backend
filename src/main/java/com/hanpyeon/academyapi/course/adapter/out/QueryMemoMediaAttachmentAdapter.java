package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.AttachmentView;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoMediaAttachmentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QueryMemoMediaAttachmentAdapter implements QueryMemoMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Override
    public List<AttachmentView> query(Long memoMediaId) {
        return mediaAttachmentRepository.findAllByMemoMedia_Id(memoMediaId).stream()
                .map(memoMediaAttachment -> new AttachmentView(memoMediaAttachment.getAttachmentId(), memoMediaAttachment.getMedia().getMediaName(), memoMediaAttachment.getMedia().getSrc()))
                .collect(Collectors.toList());
    }
}
