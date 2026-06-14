package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.service.QueryService;
import com.hanpyeon.academyapi.paging.CursorResponse;
import com.hanpyeon.academyapi.paging.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class QueryMemberController {
    private final QueryService queryService;

    @GetMapping("/teachers")
    @Operation(summary = "커서기반 선생 조회 API", description = "인증된 사용자면 모두 가능, \n" +
            "?cursorIndex={다음 커서}. 없을 시 초반부 검색\n" +
            "?size={페이지 크기 지정}. 기본 값 5 로 설정 \n" +
            "?name={찾고자 하는 이름} 시 검색, name이 없을시, 전체 조회\n")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<CursorResponse<PreviewTeacher>> queryTeachers(@RequestParam(required = false, defaultValue = "0") Long cursorIndex,
                                                                        @RequestParam(required = false, defaultValue = "5") Integer size,
                                                                        @RequestParam(required = false) String name
    ) {
        final TeacherQueryDto teacherQueryDto = new TeacherQueryDto(cursorIndex, size, name);
        CursorResponse<PreviewTeacher> teachers = queryService.loadTeachers(teacherQueryDto);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/paging")
    @Operation(summary = "페이징 선생 조회 API", description = "인증 필요, 이름 기준 오름차순 조회")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<PagedResponse<PreviewTeacher>> queryTeachers(
            @PageableDefault(size = 5) final Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        final TeacherPageQueryDto teacherQueryDto = new TeacherPageQueryDto(name, pageable);
        Page<PreviewTeacher> previewTeachers = queryService.loadTeachers(teacherQueryDto);
        return ResponseEntity.ok(PagedResponse.of(previewTeachers));
    }

    @GetMapping("/students/paging")
    @Operation(summary = "페이징 학생 조회 API", description = "인증 필요, 이름 기준 오름차순 조회")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<PagedResponse<PreviewStudent>> queryStudents(
            @PageableDefault(size = 5) final Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer startGrade,
            @RequestParam(required = false, defaultValue = "11") Integer endGrade

    ) {
        final StudentPageQueryDto studentPageQueryDto = new StudentPageQueryDto(name, startGrade, endGrade, pageable);
        Page<PreviewStudent> previewTeachers = queryService.loadStudents(studentPageQueryDto);
        return ResponseEntity.ok(PagedResponse.of(previewTeachers));
    }

    @GetMapping("/students")
    @Operation(summary = "커서 기반 학생 조회 API", description = "인증된 사용자면 모두 가능\n" +
            "?cursorIndex={다음 커서}. 없을 시 초반부 검색\n" +
            "?size={페이지 크기 지정}. 기본 값 5 로 설정\n" +
            "?name : 이름을 이용한 검색 (없을 시, 전체 검색)\n" +
            "?startRange : 기본값 0, include\n" +
            "?endRange : 기본값 11, include")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<CursorResponse<PreviewStudent>> queryStudents(@RequestParam(required = false, defaultValue = "0") Long cursorIndex,
                                                                        @RequestParam(required = false, defaultValue = "5") Integer size,
                                                                        @RequestParam(required = false) String name,
                                                                        @RequestParam(required = false, defaultValue = "0") Integer startGrade,
                                                                        @RequestParam(required = false, defaultValue = "11") Integer endGrade
    ) {
        final StudentQueryDto queryDto = new StudentQueryDto(cursorIndex, size, name, startGrade, endGrade);
        CursorResponse<PreviewStudent> students = queryService.loadStudents(queryDto);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/all")
    @Operation(summary = "전체 학생 조회 API")
    public ResponseEntity<List<PreviewStudent>> getAllStudents() {
        final List<PreviewStudent> students = queryService.loadAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/teachers/all")
    @Operation(summary = "전체 선생 조회 API")
    public ResponseEntity<List<PreviewTeacher>> getAllTeachers() {
        final List<PreviewTeacher> teachers = queryService.loadALlTeachers();
        return ResponseEntity.ok(teachers);
    }
}
