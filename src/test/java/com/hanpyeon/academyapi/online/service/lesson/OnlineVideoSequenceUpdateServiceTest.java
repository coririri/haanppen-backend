package com.hanpyeon.academyapi.online.service.lesson;

import static org.junit.jupiter.api.Assertions.*;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoRepository;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OnlineVideoSequenceUpdateServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineVideoRepository onlineVideoRepository;

    @Autowired
    private OnlineVideoSequenceUpdateService onlineVideoSequenceUpdateService;

    @Test
    void test() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final List<OnlineVideo> onlineVideos = new ArrayList<>();
        for (int i = 0; i < 100 ; i ++) {
            onlineVideos.add(new OnlineVideo(onlineCourse, null, "test" + i, true, i + 1));
        }
        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        onlineVideoRepository.saveAll(onlineVideos);

        final Long memberId = member.getId();
        final Role role = member.getRole();

        final Long courseId = onlineCourse.getId();
        final Long targetVideoId = onlineVideos.get(4).getId();
        final Integer nextSequence = 10;

        entityManager.flush();
        entityManager.clear();

        onlineVideoSequenceUpdateService.updateSequence(new UpdateOnlineVideoSequenceCommand(
                courseId,
                targetVideoId,
                nextSequence,
                memberId,
                role
        ));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(onlineVideoRepository.findById(targetVideoId).get().getVideoSequence(), nextSequence);
    }
}