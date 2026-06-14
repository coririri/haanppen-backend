package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoMediaRepository extends JpaRepository<MemoMedia, Long> {
    void deleteMemoMediaByMemo_Id(final Long memoId);
    List<MemoMedia> findAllByMemo_Id(final Long memoId);
    Optional<MemoMedia> findMemoMediaByIdAndMemo_Id(final Long memoMediaId, final Long memoId);
    List<MemoMedia> findAllByMedia(final Media media);
    void deleteAllByMedia(Media media);
}
