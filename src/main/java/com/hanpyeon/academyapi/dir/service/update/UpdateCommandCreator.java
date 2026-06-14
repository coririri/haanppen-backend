package com.hanpyeon.academyapi.dir.service.update;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryDto;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.form.resolver.DirectoryPathFormResolver;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateCommandCreator {
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryRepository directoryRepository;
    public UpdateDirectoryCommand getUpdateCommand(final UpdateDirectoryDto updateDirectoryDto) {
        final Directory directory = getDirectory(updateDirectoryDto.targetDirPath());
        return new UpdateDirectoryCommand(directory, updateDirectoryDto.newDirName(), updateDirectoryDto.requestMemberId());
    }

    private Directory getDirectory(final String targetDir) {
        final String resolvedPath = directoryPathFormResolver.resolveToAbsolutePath(targetDir);
        return directoryRepository.findDirectoryByPath(resolvedPath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
