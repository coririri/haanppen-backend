package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.port.in.QueryMemoByCourseIdAndDateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class QueryMemoByCourseIdAndDateController {

    private final QueryMemoByCourseIdAndDateUseCase queryMemoByCourseIdAndDateUseCase;
    @GetMapping(value = "/api/courses/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "날짜와 courseId로 메모 조히 API", description = "두개 이상 조회될 시 에러 MEMO_DUPLICATED_EXCEPTION 발생")
    public ResponseEntity<MemoView> loadMemo(
            @ModelAttribute @Valid final QueryMemoByCourseIdAndDateRequest queryMemoByCourseIdAndDateRequest
    ) {
        final MemoQueryByCourseIdAndDateCommand command = queryMemoByCourseIdAndDateRequest.createCommand();
        final MemoView memoView = queryMemoByCourseIdAndDateUseCase.loadSingleMemo(command);
        if (Objects.isNull(memoView)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(queryMemoByCourseIdAndDateUseCase.loadSingleMemo(command));
    }

    record QueryMemoByCourseIdAndDateRequest(
            @Nonnull Long courseId,
            @Nonnull LocalDate localDate
    ) {
        MemoQueryByCourseIdAndDateCommand createCommand() {
            return new MemoQueryByCourseIdAndDateCommand(courseId, localDate);
        }
    }
}
