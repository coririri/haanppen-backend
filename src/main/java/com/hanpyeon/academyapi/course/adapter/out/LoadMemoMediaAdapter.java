package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadMemoMediaAdapter implements LoadMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    @Override
    public List<MemoMedia> loadMedia(Long memoId) {
        return memoMediaRepository.findAllByMemo_Id(memoId)
                .stream().map(memoMedia ->  {
                    final com.hanpyeon.academyapi.media.entity.Media media = memoMedia.getMedia();
                    return MemoMedia.createByEntity(memoMedia.getId(), media.getMediaName(), media.getSrc(), null, memoMedia.getSequence());
                })
                .collect(Collectors.toList());
    }
}
