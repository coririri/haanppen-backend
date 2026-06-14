package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectoryDuplicateValidator implements DirectoryCreateValidator {

    private final DirectoryRepository directoryRepository;

    @Override
    public void validate(CreateDirectoryCommand createDirectoryCommand) {
        if (directoryRepository.findDirectoryByPath(createDirectoryCommand.newDirAbsolutePath()).isPresent()) {
            throw new DirectoryException(ErrorCode.ALREADY_EXISTS_DIRECTORY_PATH);
        }
    }
}
