package com.hanpyeon.academyapi.course.domain;

public record Student(
        Long id,
        String name,
        Integer grade
) {
    public static Student none() {
        return new Student(null, "없는 사용자", null);
    }
}
