package com.hanpyeon.academyapi.testpaper.dao;

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
@ToString(exclude = "testPaper")
public class TestPaperStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_paper_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TestPaper testPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private TestPaperStudent(final Member student, final TestPaper testPaper) {
        this.member = student;
        this.testPaper = testPaper;
    }

    public static TestPaperStudent addToTestPaper(final Member student, final TestPaper testPaper) {
        final TestPaperStudent testPaperStudent = new TestPaperStudent(student, testPaper);
        testPaper.addTestPaperStudent(testPaperStudent);
        return testPaperStudent;
    }

    public void delete() {
        this.member = null;
        this.testPaper = null;
    }
}
