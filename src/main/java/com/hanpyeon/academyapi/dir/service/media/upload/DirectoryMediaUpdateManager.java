package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger.MergedVideoUploadFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryMediaUpdateManager {

    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;
    private final MediaRepository mediaRepository;

    @Transactional
    public void update(final ChunkGroupInfo chunkGroupInfo, final String savedPath, final Long memberId) {
        final Directory directory = this.findTargetDirectory(chunkGroupInfo.getDirPath());
        final Media media = create(chunkGroupInfo.getFileName() + chunkGroupInfo.getExtension(), savedPath, memberId);
        mediaRepository.save(media);
        directory.add(media);
    }

    @Transactional
    public void update(final ChunkGroupInfo chunkGroupInfo, final Long duration, final String savedPath, final Long memberId, final Long fileSize) {
        final Directory directory = this.findTargetDirectory(chunkGroupInfo.getDirPath());
        final Media media = create(chunkGroupInfo.getFileName() + chunkGroupInfo.getExtension(), savedPath, memberId, duration, fileSize);
        mediaRepository.save(media);
        directory.add(media);
    }

    private Media create(final String fileName, final String savedPath, final Long memberId) {
        final Member member = loadMember(memberId);
        return new Media(fileName, savedPath, member);
    }

    private Media create(final String fileName, final String savedPath, final Long memberId, final Long duration, final Long fileSize) {
        final Member member = loadMember(memberId);
        return new Media(fileName, savedPath, member, duration, fileSize);
    }

    private Member loadMember(final Long memberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
    }

    private Directory findTargetDirectory(final String resolvedDirPath) {
        final Directory directory = directoryRepository.findDirectoryByPath(resolvedDirPath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        // 업로드 권한 확인로직 추가 필요
        return directory;
    }

}
