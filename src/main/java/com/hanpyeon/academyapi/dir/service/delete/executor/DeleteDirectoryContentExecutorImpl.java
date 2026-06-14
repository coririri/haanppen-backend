package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class DeleteDirectoryContentExecutorImpl implements DeleteDirectoryContentExecutor {
    private final MediaService mediaService;
    private final DirectoryRepository directoryRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(final Collection<Directory> directories) {
        directories.stream()
                .forEach(directory -> directory.getMedias().clear());
//        mediaService.delete(mediaInfos);
        directoryRepository.deleteAll(directories);
    }
}
