package com.hanpyeon.academyapi.dir.service.create;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.service.create.validate.DirectoryCreationValidateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DirectoryCreator {
    private final DirectoryCreationValidateManager directoryCreationValidateManger;

    @Transactional
    public Directory createDirectory(final CreateDirectoryCommand createDirectoryCommand) {
        directoryCreationValidateManger.validate(createDirectoryCommand);
        return new Directory(createDirectoryCommand.requestMember(), createDirectoryCommand.newDirAbsolutePath(), createDirectoryCommand.canModifyByEveryone(), createDirectoryCommand.canViewByEveryone());
    }
}
