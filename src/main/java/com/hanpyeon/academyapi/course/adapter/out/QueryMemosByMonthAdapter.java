package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.MemoAppliedDayResult;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemosByMonthPort;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryMemosByMonthAdapter implements QueryMemosByMonthPort {
    private final MemoRepository memoRepository;
    @Override
    public List<MemoAppliedDayResult> query(final LocalDate startDate, final LocalDate endDate, final Long studentId) {
        return memoRepository.findAllByMonthAndStudentId(startDate, endDate, studentId).stream()
                .map(memo -> new MemoAppliedDayResult(memo.getCourse().getId(), memo.getCourse().getCourseName(), memo.getId(), memo.getTargetDate()))
                .toList();
    }
}
