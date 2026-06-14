package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkTargetDirectoryValidator implements ChunkValidator {
    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void validate(ChunkedFile chunkedFile) {
        final Directory targetDirectory = getDirectory(chunkedFile);
        if (targetDirectory.getCanAddByEveryone()) {
            return;
        }
        if (isOwner(chunkedFile.getRequestMemberId(), targetDirectory)) {
            return;
        }
        if (isSuperUser(chunkedFile.getRequestMemberId())) {
            return;
        }
        throw new DirectoryException("디렉토리가 잠겨있거나, 본인 소유가 아닙니다", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private boolean isOwner(final Long requestMemberId, final Directory targetDirectory) {
        if (requestMemberId.equals(targetDirectory.getOwner().getId())) {
            return true;
        }
        return false;
    }

    private boolean isSuperUser(final Long requestMemberId) {
        final Member requestMember = memberRepository.findMemberByIdAndRemovedIsFalse(requestMemberId)
                .orElseThrow(() -> new NoSuchMemberException("사용자를 찾을 수 없음",ErrorCode.NO_SUCH_MEMBER));
        final Role requestMemberRole = requestMember.getRole();
        if (requestMemberRole.equals(Role.MANAGER) || requestMemberRole.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    private Directory getDirectory(final ChunkedFile chunkedFile) {
        final String targetDirectoryPath = chunkedFile.getChunkGroupInfo().getDirPath();
        return directoryRepository.findDirectoryByPath(targetDirectoryPath)
                .orElseThrow(() -> new DirectoryException("디렉토리가 존재하지 않음 : " + targetDirectoryPath, ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
