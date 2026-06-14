package com.hanpyeon.academyapi.dir.service.create;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.form.resolver.DirectoryPathFormResolver;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
class DirectoryCreateCommandCreator {
    private final MemberRepository memberRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;

    public CreateDirectoryCommand create(@Validated final CreateDirectoryDto createDirectoryDto) {
        final String parentDirPath = directoryPathFormResolver.resolveToAbsolutePath(createDirectoryDto.parentDirPath());
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(createDirectoryDto.ownerId())
                .orElseThrow(() -> new DirectoryException(ErrorCode.CANNOT_FIND_USER));
        final String absoluteDirPath = directoryPathFormResolver.resolveToAbsolutePath(parentDirPath, createDirectoryDto.directoryName());
        return new CreateDirectoryCommand(parentDirPath, createDirectoryDto.directoryName(), absoluteDirPath, createDirectoryDto.canViewByEveryone(), createDirectoryDto.canModifyByEveryone(), member);
    }
}
