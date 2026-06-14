package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.exception.InvalidCourseAccessException;
import com.hanpyeon.academyapi.course.application.port.in.MemoRegisterUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadCourseTeacherIdPort;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMemoPort;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoRegisterService implements MemoRegisterUseCase {

    private final LoadCourseTeacherIdPort loadCourseTeacherIdPort;
    private final QueryMemoByCourseIdAndDatePort queryMemoByCourseIdAndDatePort;
    private final RegisterMemoPort registerMemoPort;

    @Override
    public Long register(final MemoRegisterCommand memoRegisterCommand) {
        final Long teacherId = loadCourseTeacherIdPort.loadTeacherId(memoRegisterCommand.targetCourseId());
        isOwner(teacherId, memoRegisterCommand.requestMemberId());
        isDuplicated(memoRegisterCommand.targetCourseId(), memoRegisterCommand.registerTargetDate());

        final Memo memo = Memo.createNewMemo(memoRegisterCommand.registerTargetDate(), memoRegisterCommand.title(), memoRegisterCommand.content());
        return registerMemoPort.register(memo, memoRegisterCommand.targetCourseId());
    }

    private void isDuplicated(final Long courseId, final LocalDate localDate) {
        final MemoView memoView = queryMemoByCourseIdAndDatePort.query(new MemoQueryByCourseIdAndDateCommand(courseId, localDate));
        if (memoView == null) {
            return;
        }
        throw new CourseException("같은 날짜에 두개이상의 메모 등록 불가",ErrorCode.MEMO_DUPLICATED_EXCEPTION);
    }

    private void isOwner(final Long courseTeacherId, final Long requestMemberId) {
        if (!courseTeacherId.equals(requestMemberId)) {
            throw new InvalidCourseAccessException("반 담당 선생님이 아닙니다", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }
}
