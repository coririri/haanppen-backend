package com.hanpyeon.academyapi.course.domain;

public record Teacher(
        Long id,
        String name
) {
    public static Teacher none() {
        return new Teacher(null, "없는 사용자");
    }
}
