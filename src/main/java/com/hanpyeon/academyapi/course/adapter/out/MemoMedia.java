package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class MemoMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMO_MEDIA_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEDIA", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;
    @ManyToOne
    @JoinColumn(name = "MEMO_MEMO_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Memo memo;
    private Integer sequence;
    @OneToMany(mappedBy = "memoMedia")
    private List<MemoMediaAttachment> memoMediaAttachments = new ArrayList<>();

    private MemoMedia(final Memo memo, final Media media, final Integer sequence) {
        this.memo = memo;
        this.media = media;
        this.sequence = sequence;
    }

    void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    public void setNull() {
        this.media = null;
        this.memo = null;
        this.memoMediaAttachments.stream()
                .forEach(memoMediaAttachment -> memoMediaAttachment.setNull());
    }

    static MemoMedia of(final Memo memo, final Media media, final Integer sequence) {
        return new MemoMedia(memo, media, sequence);
    }
}
