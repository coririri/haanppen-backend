package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountRemoveCommand;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRemoveService {
    private final MemberRepository memberRepository;

    @Transactional
    public void removeAccount(final AccountRemoveCommand accountRemoveCommand) {
        List<Member> members = memberRepository.findAllById(accountRemoveCommand.targetIds());
        members.stream().forEach(member -> member.remove());
    }
}
