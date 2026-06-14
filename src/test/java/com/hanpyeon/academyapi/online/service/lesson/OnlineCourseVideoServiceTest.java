package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand;
import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand.OnlineVideoCommand;
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

class OnlineCourseVideoServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private OnlineVideoRegisterService onlineCourseVideoService;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;

    @Test
    @Transactional
    void videoAddServiceTest() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final Media media = new Media("test", "src", member);

        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        mediaRepository.save(media);

        entityManager.flush();
        entityManager.clear();

        final AddOnlineVideoCommand addOnlineVideoCommand = new AddOnlineVideoCommand(onlineCourse.getId(), new OnlineVideoCommand(media.getSrc(), true),
                member.getId(), member.getRole());

        onlineCourseVideoService.addOnlineVideo(addOnlineVideoCommand);

        entityManager.flush();
        entityManager.clear();

        final OnlineVideo onlineVideo = onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourse.getId())
                .orElseThrow()
                .getVideos().get(0);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(onlineVideo.getPreview(), true);
            Assertions.assertEquals(onlineVideo.getMedia().getId(), media.getId());
            Assertions.assertEquals(onlineVideo.getVideoSequence(), 1);
        });
    }
}