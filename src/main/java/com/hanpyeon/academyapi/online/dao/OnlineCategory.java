package com.hanpyeon.academyapi.online.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
public class OnlineCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "onlineCategory", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<OnlineCourse> onlineCourses;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<OnlineCategory> childCategories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCategory parentCategory;
    @Column(nullable = false, unique = true)
    private String categoryName;
    @Column
    private Long level = 1L;
    @CreationTimestamp
    private LocalDateTime creationTime;

    public OnlineCategory(final String categoryName) {
        this.categoryName = categoryName;
    }

    public void setParentCategory(final OnlineCategory parentCategory) {
        this.parentCategory = parentCategory;
        parentCategory.childCategories.add(this);

        this.level = parentCategory.getLevel() + 1;
    }
}
