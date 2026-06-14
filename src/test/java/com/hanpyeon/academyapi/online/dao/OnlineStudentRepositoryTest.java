package com.hanpyeon.academyapi.online.dao;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class OnlineStudentRepositoryTest {

    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void deleteQueryTest() {
        final OnlineCourse onlineCourse = new OnlineCourse(null, "test");
        final List<OnlineStudent> onlineStudents = List.of(
                new OnlineStudent(onlineCourse, null),
                new OnlineStudent(onlineCourse, null),
                new OnlineStudent(onlineCourse, null)
        );
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(onlineStudents);

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(onlineStudentRepository.count(), 3);
        onlineStudentRepository.removeAllByOnlineCourseId(1L);
        Assertions.assertEquals(onlineStudentRepository.count(), 0);
        Assertions.assertEquals(onlineCourseRepository.count(), 1);
    }
}