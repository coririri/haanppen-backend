package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.MemberManager;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MemberManagerTest {

    @Mock
    MemberRepository memberRepository;
    MemberManager manager;

    @BeforeEach
    void init() {
        manager = new MemberManager(memberRepository);
    }

    @Test
    void 멤버_검증_후_가져오기_성공_테스트() {
        Member testMember = Member.builder()
                .role(Role.STUDENT)
                .build();
        Mockito.when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(testMember));

        assertThat(manager.getMemberWithValidated(Mockito.anyLong(), member -> member.getRole().equals(Role.STUDENT)));
    }

    @Test
    void 멤버_검증_후_가져오기_실패_테스트() {
        Member testMember = Member.builder()
                .role(Role.STUDENT)
                .build();
        Mockito.when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(testMember));

        assertThatThrownBy(() -> manager.getMemberWithValidated(Mockito.anyLong(), member -> member.getRole().equals(Role.TEACHER))
        ).isInstanceOf(NoSuchMemberException.class);
    }
}