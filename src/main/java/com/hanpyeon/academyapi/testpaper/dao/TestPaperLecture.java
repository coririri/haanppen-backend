package com.hanpyeon.academyapi.testpaper.dao;

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
public class TestPaperLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_PAPER_LECTURE_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_paper_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), unique = true)
    private TestPaper testPaper;

    @Column(name = "lecture_name", nullable = false)
    private String lectureName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "directory_path")
    private String directoryPath;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public TestPaperLecture(final TestPaper testPaper, final String lectureName) {
        this.testPaper = testPaper;
        this.lectureName = lectureName;
    }

    public void updateLectureName(final String newLectureName) {
        if (newLectureName != null && !newLectureName.isBlank()) {
            this.lectureName = newLectureName;
        }
    }

    public void updateDescription(final String newDescription) {
        this.description = newDescription;
    }

    public void updateDirectoryPath(final String newDirectoryPath) {
        this.directoryPath = newDirectoryPath;
    }
}
