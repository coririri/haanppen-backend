package com.hanpyeon.academyapi.testpaper.dao;

import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TestPaperLectureVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_PAPER_LECTURE_VIDEO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_paper_lecture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TestPaperLecture testPaperLecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;

    @Column(name = "video_name", nullable = false)
    private String videoName;

    @Column(name = "video_sequence", nullable = false)
    private Integer videoSequence;

    public TestPaperLectureVideo(final TestPaperLecture testPaperLecture, final Media media, final String videoName, final Integer videoSequence) {
        this.testPaperLecture = testPaperLecture;
        this.media = media;
        this.videoName = videoName;
        this.videoSequence = videoSequence;
    }

    public void updateVideoName(final String videoName) {
        if (videoName != null && !videoName.isBlank()) {
            this.videoName = videoName;
        }
    }

    public void updateSequence(final Integer sequence) {
        if (sequence != null && sequence >= 0) {
            this.videoSequence = sequence;
        }
    }

    public void setNull() {
        this.testPaperLecture = null;
        this.media = null;
    }
}
