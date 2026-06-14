package com.hanpyeon.academyapi.online.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineCourseRepository extends JpaRepository<OnlineCourse, Long> {
    List<OnlineCourse> findAllByTeacherId(final Long teacherId);

    @Query("SELECT o FROM OnlineCourse o JOIN OnlineStudent s ON o.id = s.course.id WHERE s.member.id = :studentId")
    List<OnlineCourse> findAllByStudentId(@Param("studentId") final Long studentId);

    @Query("SELECT oc from OnlineCourse oc "
            + "LEFT JOIN FETCH oc.teacher "
            + "LEFT JOIN FETCH oc.onlineStudents "
            + "WHERE oc.id = :onlineCourseId")
    OnlineCourse findOnlineCourse(@Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT oc FROM OnlineCourse oc LEFT JOIN FETCH oc.onlineCategory WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadCourseAndCategoryByCourseId(@Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT oc FROM OnlineCourse oc JOIN FETCH oc.teacher LEFT JOIN FETCH oc.videos WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadCourseAndVideosAndTeacherByCourseId(@Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT oc FROM OnlineCourse oc JOIN FETCH oc.teacher JOIN FETCH oc.onlineStudents WHERE oc.onlineCategory.id = :categoryId OR oc.onlineCategory.parentCategory.id = :categoryId")
    List<OnlineCourse> loadOnlineCoursesByCategoryId(@Param("categoryId") final Long categoryId);

    @Query("SELECT oc FROM OnlineCourse oc LEFT JOIN FETCH oc.onlineCategory LEFT JOIN FETCH oc.videos WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadOnlineCourseAndVideosAndCategoryById(@Param("onlineCourseId") final Long courseId);

    @Query("SELECT oc FROM OnlineCourse oc LEFT JOIN FETCH oc.videos WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadOnlineCourseAndVideosByCourseId(@Param("onlineCourseId") final Long courseId);

    @Modifying
    @Query("DELETE OnlineVideoAttachment ova WHERE ova.onlineVideo.id IN :videoIds")
    void removeAllOnlineVideoAttachmentsIn(@Param("videoIds") final List<Long> videoIds);

    @Modifying
    @Query("DELETE OnlineVideo ov WHERE ov.onlineCourse.id = :onlineCourseId")
    void removeOnlineCourseVideos(@Param("onlineCourseId") final Long courseId);
}
