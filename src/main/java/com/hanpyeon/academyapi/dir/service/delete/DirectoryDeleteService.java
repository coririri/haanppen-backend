package com.hanpyeon.academyapi.dir.service.delete;

import com.hanpyeon.academyapi.dir.dto.DeleteDirectoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryDeleteService {
    private final DirectoryDeleteCommandCreator directoryDeleteCommandCreator;
    private final DirectoryDeleteManager directoryDeleteManager;

    @Transactional
    public void delete(final DeleteDirectoryDto deleteDirectoryDto) {
        final DirectoryDeleteCommand directoryDeleteCommand = directoryDeleteCommandCreator.create(deleteDirectoryDto);
        directoryDeleteManager.delete(directoryDeleteCommand);
    }
}
