package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.MemoAppliedDayResult;
import com.hanpyeon.academyapi.course.application.port.in.QueryCourseByMonthUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
public class QueryMemoByMonthController {
    private final QueryCourseByMonthUseCase queryCourseByMonthUseCase;

    @GetMapping("/api/courses/memos/month")
    @Operation(summary = "로그인된 학생 ID와 날짜정보를 활용하여, 해당 달의 학생 수업 정보를 가져오는 API")
    public ResponseEntity<List<MemoAppliedDayResult>> queryByMonthInfo(
            @RequestParam(required = true) final LocalDate monthInfo,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(queryCourseByMonthUseCase.query(monthInfo, memberPrincipal.memberId()));
    }
}
