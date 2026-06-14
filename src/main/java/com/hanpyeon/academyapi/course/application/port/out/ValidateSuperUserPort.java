package com.hanpyeon.academyapi.course.application.port.out;

public interface ValidateSuperUserPort {
    Boolean isSuperUser(final Long memberId);
}
