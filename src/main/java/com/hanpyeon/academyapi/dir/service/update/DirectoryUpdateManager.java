package com.hanpyeon.academyapi.dir.service.update;

import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class DirectoryUpdateManager {
    private final List<DirectoryUpdateHandler> updateHandlers;

    @Transactional
    public void update(final UpdateDirectoryCommand updateDirectoryCommand) {
        updateHandlers.stream()
                .forEach(directoryUpdateHandler -> directoryUpdateHandler.update(updateDirectoryCommand));
    }
}
