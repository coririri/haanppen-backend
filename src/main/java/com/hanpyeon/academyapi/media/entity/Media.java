package com.hanpyeon.academyapi.media.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.course.adapter.out.MemoMedia;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_media_source", columnList = "src"))
@SQLDelete(sql = "UPDATE media SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Media {
    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    @Column(nullable = false)
    private String mediaName;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(nullable = false, unique = true)
    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_owner", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "duration", nullable = true)
    private Long duration;

    @Column(name = "size", nullable = true)
    private Long size;

    private boolean deleted = false;  // soft delete flag

    public Media(String mediaName, String src, Member member) {
        this.mediaName = mediaName;
        this.src = src;
        this.member = member;
    }

    public Media(String mediaName, String src, Member member, Long duration, Long size) {
        this.mediaName = mediaName;
        this.src = src;
        this.member = member;
        this.duration = duration;
        this.size = size;
    }
}
