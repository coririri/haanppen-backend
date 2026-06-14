package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.course.adapter.out.CourseStudent;
import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Boolean removed = false;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredDate;

    @Column(name = "verify_code", nullable = true)
    private String verificationCode;

    @Column(name = "verify_message_send_count", nullable = true)
    private Integer verifyMessageSendCount = 0;

    @Column(name = "verify_message_request_time", nullable = true)
    private LocalDateTime verifyDateTime;

    @Column(name = "verify_status", nullable = true)
    private Boolean isVerifying = false;

    @OneToMany(mappedBy = "member")
    private List<CourseStudent> courseStudent = new ArrayList<>();

    @Column(name = "login_try_count", nullable = false)
    private Integer loginTryCount = 0;

    @Column(name = "locked", nullable = true)
    private Boolean locked = false;

    @Column(name = "locked_start_time", nullable = true)
    @Getter(AccessLevel.PRIVATE)
    private LocalDateTime lockedStartTime;

    public void setName(final String name) {
        this.name = name;
    }

    public void setGrade(final Integer grade) {
        this.grade = grade;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public void setVerificationCode(final String verificationCode) {
        this.verificationCode = verificationCode;
        this.verifyDateTime = LocalDateTime.now();
        if (Objects.isNull(verifyMessageSendCount)) {
            verifyMessageSendCount = 0;
        }
        isVerifying = true;
        verifyMessageSendCount ++;
    }

    public boolean isOverMaxLoginTryCount(final Integer maxLoginTryCount) {
        return this.loginTryCount >= maxLoginTryCount;
    }

    public void increaseLoginTryCount() {
        this.loginTryCount++;
    }

    public boolean canLoginAt(final LocalDateTime currentTime, final Long lockTimeMinutes) {
        if (!getLocked()) {
            return true;
        }
        return currentTime.isAfter(getLockedStartTime().plusMinutes(lockTimeMinutes));
    }

    public void lock(final LocalDateTime lockedStartTime) {
        this.locked = true;
        this.lockedStartTime = lockedStartTime;
    }

    public void unlock() {
        this.loginTryCount = 0;
        this.locked = false;
        this.lockedStartTime = null;
    }

    public void resetVerifyInfo() {
        this.verifyDateTime = null;
        this.verifyMessageSendCount = 0;
        this.verificationCode = null;
        this.isVerifying = false;
    }

    public void remove() {
        this.phoneNumber = null;
        this.removed = true;
        this.courseStudent.stream()
                .forEach(courseStudent1 -> courseStudent1.delete());
    }

    @Builder
    private Member(String phoneNumber, String name, String encryptedPassword, Integer grade, Role role, LocalDateTime registeredDate) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = encryptedPassword;
        this.grade = grade;
        this.role = role;
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memberName='" + name + '\'' +
                ", password='" + "****" + '\'' +
                ", grade=" + grade +
                ", userRole=" + role +
                ", localDateTime=" + registeredDate +
                '}';
    }

    public static Member none() {
        return Member.builder().build();
    }
}
