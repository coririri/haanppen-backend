package com.hanpyeon.academyapi.dir.service.delete;

import com.hanpyeon.academyapi.dir.service.delete.executor.DeleteDirectoryProcessor;
import com.hanpyeon.academyapi.dir.service.delete.validate.DirectoryDeleteValidateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DirectoryDeleteManager {

    private final DirectoryDeleteValidateManager directoryDeleteValidateManager;
    private final DeleteDirectoryProcessor deleteDirectoryProcessor;

    public void delete(final DirectoryDeleteCommand directoryDeleteCommand) {
        directoryDeleteValidateManager.validate(directoryDeleteCommand);
        deleteDirectoryProcessor.delete(directoryDeleteCommand);
    }
}
