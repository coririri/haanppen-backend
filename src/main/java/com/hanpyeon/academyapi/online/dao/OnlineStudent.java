package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
public class OnlineStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_course", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCourse course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_course_student", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registeredDateTIme;

    public OnlineStudent(final OnlineCourse course, final Member member) {
        course.getOnlineStudents().add(this);
        this.course = course;
        this.member = member;
    }
}
