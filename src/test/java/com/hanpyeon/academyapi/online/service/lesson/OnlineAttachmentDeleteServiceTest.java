package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachment;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachmentRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideoRepository;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineVideoAttachmentCommand;
import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OnlineAttachmentDeleteServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineVideoRepository onlineVideoRepository;
    @Autowired
    private OnlineVideoAttachmentRepository onlineVideoAttachmentRepository;

    @Autowired
    private OnlineAttachmentDeleteService onlineAttachmentDeleteService;

    @Test
    @Transactional
    void 온라인강의_첨부파일_삭제() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();

        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final Media media = new Media("test", "src", member);
        final OnlineVideo onlineVideo = new OnlineVideo(onlineCourse, media, "name", true, 1);
        final OnlineVideoAttachment onlineVideoAttachment = new OnlineVideoAttachment(onlineVideo,"title", "test");

        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        mediaRepository.save(media);
        onlineVideoRepository.save(onlineVideo);
        onlineVideoAttachmentRepository.save(onlineVideoAttachment);

        entityManager.flush();

        final Long onlineVideoId = onlineVideo.getId();
        final Long memberId = member.getId();
        final Role memberRole = member.getRole();
        final Long onlineVideoAttachmentId = onlineVideoAttachment.getId();
        final Long attachmentId = onlineVideoAttachment.getId();

        onlineAttachmentDeleteService.deleteAttachment(new DeleteOnlineVideoAttachmentCommand(
                onlineVideoAttachmentId,
                onlineVideoId,
                memberId,
                memberRole
        ));

        entityManager.flush();

        Assertions.assertTrue(onlineVideoAttachmentRepository.findById(attachmentId).isEmpty());
    }
}