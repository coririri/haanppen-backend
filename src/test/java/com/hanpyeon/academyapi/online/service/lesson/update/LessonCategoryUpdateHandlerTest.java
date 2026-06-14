package com.hanpyeon.academyapi.online.service.lesson.update;

import com.hanpyeon.academyapi.online.dao.OnlineCategory;
import com.hanpyeon.academyapi.online.dao.OnlineCategoryRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class LessonCategoryUpdateHandlerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OnlineCategoryRepository onlineCategoryRepository;

    @Test
    void testOnlineCategoryQuery() {
        final OnlineCategory onlineCategory = new OnlineCategory("test1");
        final Long id = onlineCategoryRepository.save(onlineCategory).getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThat(onlineCategoryRepository.findById(id))
                .isNotNull();
    }
}