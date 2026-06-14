package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;

interface DirectoryCreateValidator {
    void validate(final CreateDirectoryCommand createDirectoryCommand);
}
