package com.hanpyeon.academyapi.dir.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_path", columnList = "directory_path"))
@NoArgsConstructor
@Getter
@ToString
@Slf4j
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "directory_owner", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member owner;
    @Column(name = "directory_path", nullable = false, unique = true)
    private String path;
    @Column(name = "directory_viewable", nullable = false)
    private Boolean canViewByEveryone;
    @Column(name = "directory_addable", nullable = false)
    private Boolean canAddByEveryone;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @OneToMany
    private List<Media> medias;

    public Directory(Member owner, String path, Boolean canModifyByEveryone, Boolean canViewByEveryone) {
        this.owner = owner;
        this.path = path;
        this.canAddByEveryone = canModifyByEveryone;
        this.canViewByEveryone = canViewByEveryone;
    }

    public void add(final Media media) {
        medias.add(media);
    }

    public void setPath(final String newPath) {
        this.path = newPath;
    }
}
