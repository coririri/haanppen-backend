package com.hanpyeon.academyapi.banner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;
    @Column
    private String content;
    @CreationTimestamp
    private LocalDateTime lastModified;

    public Banner(String content) {
        this.content = content;
    }

    void changeContent(final String content) {
        this.content = content;
        this.lastModified = LocalDateTime.now();
    }
}
