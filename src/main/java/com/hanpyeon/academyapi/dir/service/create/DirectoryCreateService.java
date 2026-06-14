package com.hanpyeon.academyapi.dir.service.create;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class DirectoryCreateService {

    private final DirectoryCreator directoryCreator;
    private final DirectoryRepository directoryRepository;
    private final DirectoryCreateCommandCreator directoryCreateCommandCreator;

    @Transactional
    public void addNewDirectory(@Validated final CreateDirectoryDto createDirectoryDto) {
        final CreateDirectoryCommand createDirectoryCommand = directoryCreateCommandCreator.create(createDirectoryDto);
        final Directory directory = directoryCreator.createDirectory(createDirectoryCommand);
        directoryRepository.save(directory);
    }
}
