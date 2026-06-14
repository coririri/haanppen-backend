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
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class OnlineVideoAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineVideo onlineVideo;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @CreationTimestamp
    private LocalDateTime registeredDate;

    public OnlineVideoAttachment(final OnlineVideo onlineVideo, final String title, final String content) {
        this.onlineVideo = onlineVideo;
        this.title = title;
        this.content = content;
    }
}
