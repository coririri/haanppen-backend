package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.media.entity.Image;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class OnlineCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ONLINE_COURSE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCategory onlineCategory;

    @OneToMany(mappedBy = "onlineCourse")
    private List<OnlineVideo> videos = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @BatchSize(size = 10)
    private List<OnlineStudent> onlineStudents = new ArrayList<>();

    @Column(name = "courseName", nullable = false)
    private String courseName;

    @Column(name = "courseTitle", nullable = true)
    private String courseTitle;

    @Column(name = "courseRange", nullable = true)
    private String courseRange;

    @Column(name = "courseContent", nullable = true)
    private String courseContent;

    @Column
    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Image image;

    public OnlineCourse(final Member teacher, final String courseName) {
        this.teacher = teacher;
        this.courseName = courseName;
    }

    public void setOnlineCategory(final OnlineCategory onlineCategory) {
        this.onlineCategory = onlineCategory;
    }

    public void setCourseTitle(final String title) {
        this.courseTitle = title;
    }

    public void setTeacher(final Member teacher) {
        this.teacher = teacher;
    }

    public void setCourseRange(final String courseRange) {
        this.courseRange = courseRange;
    }

    public void setCourseContent(final String courseDescribe) {
        this.courseContent = courseDescribe;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public void clearContents() {
        this.courseTitle = null;
        this.courseContent = null;
        this.courseRange = null;
    }

    public List<OnlineStudent> getOnlineStudents() {
        return onlineStudents.stream()
                .filter(onlineStudent -> !onlineStudent.getMember().getRemoved())
                .collect(Collectors.toList());
    }

//    public void setOnlineStudents(final List<OnlineStudent> onlineStudents) {
//        onlin
//    }
}
