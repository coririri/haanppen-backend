package com.hanpyeon.academyapi.dir.service.delete.validate;

import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryValidator {
    void validate(final DirectoryDeleteCommand directoryDeleteCommand);
}
