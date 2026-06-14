package com.hanpyeon.academyapi.board.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.media.entity.Image;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = true)
    private String content;
    private Boolean adopted = false;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @ManyToOne
    @JoinColumn(name = "registeredMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member registeredMember;
    @ManyToOne(targetEntity = Question.class, optional = false)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Question question;
    @OneToMany
    private List<Image> images;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", adopted=" + adopted +
                ", registeredDateTime=" + registeredDateTime +
                ", registeredMember=" + registeredMember +
                ", question=" + question +
                ", images=" + images +
                '}';
    }

    @Builder
    public Comment(String content, Member registeredMember, Question question, List<Image> images) {
        this.content = content;
        this.registeredMember = registeredMember;
        this.question = question;
        this.images = images;
    }

    void setQuestion(final Question question) {
        this.question = question;
    }
    void singleAdopt() {
        this.adopted = true;
    }
    void singleDeAdopt() {
        this.adopted = false;
    }
    public void adopt() {
        question.clearCommentsAdoptedStatus();
        question.singleSolved();
        singleAdopt();
    }
    public void deAdopt() {
        question.singleUnsolved();
        question.clearCommentsAdoptedStatus();
    }

    public void delete() {
        this.question = null;
        this.registeredMember = null;
        this.images.clear();
    }
    public void setContent(final String content) {
        this.content = content;
    }
    public void changeImagesTo(final List<Image> images) {
        this.images = images;
    }
}
