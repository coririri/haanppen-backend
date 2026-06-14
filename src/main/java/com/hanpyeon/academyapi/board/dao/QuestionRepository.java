package com.hanpyeon.academyapi.board.dao;

import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findQuestionById(Long id);
    Slice<Question> findBy(Pageable pageable);
    List<Question> findQuestionsByIdIsGreaterThanEqual(final Long id, final Pageable pageable);
    List<Question> findQuestionsByIdIsGreaterThanEqualAndAndOwnerMemberId(final Long id, final Long memberId, final Pageable pageable);
    Page<Question> findQuestionsByOwnerMemberId(final Long memberId, final Pageable pageable);
    Page<Question> findQuestionsByOwnerMemberIdAndTitleContaining(final Long memberId, final String title, final Pageable pageable);
    Page<Question> findAllByTitleContaining(final String title, final Pageable pageable);
    Page<Question> findAllBy(final Pageable pageable);
}
