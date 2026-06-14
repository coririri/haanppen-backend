package com.hanpyeon.academyapi.account.repository;

import com.hanpyeon.academyapi.account.dto.MemberInfo;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumberAndRemovedIsFalse(final String phoneNumber);

    Optional<Member> findMemberByPhoneNumberAndRemovedIsFalse(final String phoneNumber);

    Optional<Member> findMemberByIdAndRemovedIsFalse(final Long id);

    Optional<Member> findMemberByIdAndRoleAndRemovedIsFalse(final Long id, final Role role);

    List<Member> findMembersByIdIsInAndRoleAndRemovedIsFalse(final List<Long> ids, final Role role);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.role = :targetRole AND m.removed = FALSE")
    List<MemberInfo> findMembersByRole(@Param("targetRole") final Role role);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.name LIKE %:targetName AND m.id >= :cursorIndex AND m.role = :targetRole AND m.removed = FALSE ")
    List<MemberInfo> findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("targetName") final String name);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.removed = FALSE")
    List<MemberInfo> findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM  Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    List<MemberInfo> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND  :includedEndGrade AND m.name LIKE %:targetName% AND m.removed = FALSE")
    List<MemberInfo> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, @Param("targetName") final String name);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.removed = FALSE")
    Page<MemberInfo> findMembersByRoleAndRemovedIsFalse(@Param("targetRole") final Role role, final Pageable pageable);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    Page<MemberInfo> findMembersByRoleAndGradeBetweenAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, final Pageable pageable);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.name LIKE %:targetName% AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    Page<MemberInfo> findMembersByRoleAndNameContainingAndGradeBetweenAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("targetName") final String name, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, final Pageable pageable);

    @Query("SELECT new com.hanpyeon.academyapi.account.dto.MemberInfo(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.name LIKE %:targetName% AND m.removed = FALSE")
    Page<MemberInfo> findMembersByRoleAndNameContainingAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("targetName") final String name, final Pageable pageable);
}
