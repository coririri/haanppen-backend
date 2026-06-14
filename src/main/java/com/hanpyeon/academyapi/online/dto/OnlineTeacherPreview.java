package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;

public record OnlineTeacherPreview(
        String teacherName,
        Long teacherId
) {
	public static OnlineTeacherPreview from(final Member member) {
		if (member == null) {
			return new OnlineTeacherPreview(null, null);
		}
		return new OnlineTeacherPreview(member.getName(), member.getId());
	}
}
