package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString(exclude = "courseEntity")
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Course courseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private CourseStudent(final Member student, final Course courseEntity) {
        this.member = student;
        this.courseEntity = courseEntity;
    }

    static CourseStudent addToCourse(final Member student, final Course courseEntity) {
        final CourseStudent courseStudent = new CourseStudent(student, courseEntity);
        courseEntity.addCourseStudent(courseStudent);
        return new CourseStudent(student, courseEntity);
    }

    public void delete() {
        this.member = null;
        this.courseEntity = null;
    }
}
