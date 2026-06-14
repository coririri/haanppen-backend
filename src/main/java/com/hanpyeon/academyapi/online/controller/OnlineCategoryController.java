package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.OnlineCategoryAddCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCategoryAddRequest;
import com.hanpyeon.academyapi.online.dto.OnlineCategoryInfo;
import com.hanpyeon.academyapi.online.service.category.OnlineCategoryAddService;
import com.hanpyeon.academyapi.online.service.category.OnlineCategoryDeleteService;
import com.hanpyeon.academyapi.online.service.category.OnlineCategoryQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses/category")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class OnlineCategoryController {
    private final OnlineCategoryAddService onlineCategoryAddService;
    private final OnlineCategoryQueryService onlineCategoryQueryService;
    private final OnlineCategoryDeleteService onlineCategoryDeleteService;

    @PostMapping
    @Operation(summary = "온라인 강의 카테고리 등록 API", description = "매니저, 어드민 권한만 사용 가능")
    public ResponseEntity<?> addCategory(
            @RequestBody @Valid final OnlineCategoryAddRequest onlineCategoryAddRequest
    ) {
        final OnlineCategoryAddCommand command = onlineCategoryAddRequest.toCommand();
        onlineCategoryAddService.addOnlineCategory(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "온라인 강의 자식 카테고리 서비스")
    public ResponseEntity<List<OnlineCategoryInfo>> queryChildCategories(
            @PathVariable(required = true) final Long categoryId
    ) {
        return ResponseEntity.ok(onlineCategoryQueryService.queryChildCategories(categoryId));
    }

    @GetMapping("/root")
    @Operation(summary = "온라인 강의 카테고리 조회 서비스")
    public ResponseEntity<List<OnlineCategoryInfo>> queryRootCategories() {
        return ResponseEntity.ok(onlineCategoryQueryService.queryRootCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteOnlineCategory(
            @PathVariable(required = true) final Long categoryId
    ) {
        onlineCategoryDeleteService.deleteChildCategories(categoryId);
        return ResponseEntity.noContent().build();
    }
}
