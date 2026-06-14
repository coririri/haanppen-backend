package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoTextPort;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateMemoTextAdapter implements UpdateMemoTextPort {
    private final MemoRepository memoRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Memo memo) {
        final com.hanpyeon.academyapi.course.adapter.out.Memo memoEntity = getMemo(memo.getMemoId());
        memoEntity.setContent(memo.getContent());
        memoEntity.setTitle(memo.getTitle());
    }

    private com.hanpyeon.academyapi.course.adapter.out.Memo getMemo(final Long memoId) {
        return memoRepository.findById(memoId)
                .orElseThrow(() -> new CourseException(ErrorCode.MEMO_NOT_EXIST));
    }
}
