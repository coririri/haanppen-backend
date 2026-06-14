package com.hanpyeon.academyapi.online.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineVideoAttachmentRepository extends JpaRepository<OnlineVideoAttachment, Long> {

    @Query("SELECT ova FROM OnlineVideoAttachment ova JOIN FETCH ova.onlineVideo JOIN FETCH ova.onlineVideo.onlineCourse WHERE ova.id = :videoAttachmentId")
    Optional<OnlineVideoAttachment> findWithOnlineCourseAndVideosById(@Param("videoAttachmentId") final Long videoAttachmentId);
}
