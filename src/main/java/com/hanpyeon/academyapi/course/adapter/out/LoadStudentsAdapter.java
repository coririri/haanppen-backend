package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class LoadStudentsAdapter implements LoadStudentsPort {

    private final MemberRepository memberRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Student> loadStudents(final List<Long> memberIds) {
        int requestMemberCount = memberIds.size();

        List<Student> students = loadAllStudent(memberIds);
//        if (students.size() != requestMemberCount) {
//            throw new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER);
//        }
        return students;
    }

    public List<Student> loadAllStudent(final List<Long> memberIds) {
        return memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(memberIds, Role.STUDENT).stream()
                .map(courseMapper::mapToStudent)
                .toList();
    }
}
