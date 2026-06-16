package com.hanpyeon.academyapi.testpaper.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TEST_PAPER")
@NoArgsConstructor
@Getter
@ToString
public class TestPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_PAPER_ID")
    private Long id;

    @Column(name = "testPaperName", nullable = false)
    private String testPaperName;

    @CreationTimestamp
    @Column(name = "registeredDateTime")
    private LocalDateTime registeredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member teacher;

    @OneToMany(mappedBy = "testPaper")
    @BatchSize(size = 10)
    private List<TestPaperStudent> students = new ArrayList<>();

    public TestPaper(final String testPaperName, final Member teacher) {
        this.testPaperName = testPaperName;
        this.teacher = teacher;
    }

    public void changeTestPaperName(final String newTestPaperName) {
        this.testPaperName = newTestPaperName;
    }

    public void changeTeacher(final Member newTeacher) {
        this.teacher = newTeacher;
    }

    public void addTestPaperStudent(final TestPaperStudent testPaperStudent) {
        this.students.add(testPaperStudent);
    }

    public List<TestPaperStudent> getTestPaperStudents() {
        return this.students;
    }
}
