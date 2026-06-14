package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.media.entity.Media;
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
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Getter
public class OnlineVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCourse onlineCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;

    @Column(name = "video_name", nullable = false)
    private String videoName;

    @Column(name = "vidoe_previewable", nullable = false)
    private Boolean preview = false;

    @Column(name = "video_sequence", nullable = false)
    private Integer videoSequence;

    @OneToMany(mappedBy = "onlineVideo", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<OnlineVideoAttachment> videoAttachments;

    public OnlineVideo(OnlineCourse onlineCourse, Media media, String videoName, Boolean preview, Integer videoSequence) {
        this.onlineCourse = onlineCourse;
        this.media = media;
        this.videoName = videoName;
        this.preview = preview;
        this.videoSequence = videoSequence;
    }

    public void changeSequence(final OnlineVideo targetVideo) {
        final Integer tempSequence = targetVideo.getVideoSequence();
        targetVideo.videoSequence = this.videoSequence;
        this.videoSequence = tempSequence;
    }

    public void setPreviewStatus(final boolean previewStatus) {
        this.preview = previewStatus;
    }
}
