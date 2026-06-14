package com.hanpyeon.academyapi.dir.service.update;

import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryCommand;

interface DirectoryUpdateHandler {
    void update(final UpdateDirectoryCommand updateDirectoryCommand);
}
