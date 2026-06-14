package com.hanpyeon.academyapi.dir.service.update;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryCommand;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class DirectoryNameUpdateHandler implements DirectoryUpdateHandler {
    private final DirectoryRepository directoryRepository;

    /**
     * @param updateDirectoryCommand 주어진 대상 디렉토리들을 모두 로드하여 엔티티자체에 새로운 이름을 반영하는게 아닌, 변경될 path를 미리 조회하여 violation 확인.
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(UpdateDirectoryCommand updateDirectoryCommand) {
        final Directory targetDirectory = updateDirectoryCommand.directory();
        isRootDirectory(targetDirectory);
        final String newDirectoryPath = getNewDirectoryPath(targetDirectory.getPath(), updateDirectoryCommand.newDirName());
        final String prevDirPath = targetDirectory.getPath();

        List<Directory> directories = directoryRepository.queryChildDirectories(updateDirectoryCommand.directory().getPath());
        List<UpdatingDirectory> updateTargets = new ArrayList<>();
        for (final Directory directory : directories) {
            final String updatedPath = getNewPath(directory.getPath(), prevDirPath, newDirectoryPath);
            updateTargets.add(new UpdatingDirectory(directory, updatedPath));
        }
        update(updateTargets);
    }

    private void update(final List<UpdatingDirectory> updatingDirectories) {
        validate(updatingDirectories);
        updatingDirectories.stream()
                .forEach(updatingDirectory -> updatingDirectory.update());
    }

    private void validate(final List<UpdatingDirectory> updatingDirectories) {
        final List<String> newPaths = updatingDirectories.stream()
                .map(updatingDirectory -> updatingDirectory.newDirPath)
                .collect(Collectors.toList());
        if (directoryRepository.existsAllByPathIn(newPaths)) {
            throw new DirectoryException("동일한 경로에 같은 이름의 디렉토리가 존재합니다.", ErrorCode.ALREADY_EXISTS_DIRECTORY_PATH);
        }
    }

    private String getNewPath(final String prevPath, final String targetPath, final String newPath) {
        final String temp = prevPath.substring(targetPath.length(), prevPath.length());
        return newPath + temp;
    }

    private void isRootDirectory(final Directory directory) {
        if (directory.getPath().equals("/")) {
            throw new DirectoryException("루트 디렉토리는 누구도 수정할 수 없습니다", ErrorCode.DIRECTORY_CANNOT_MODIFY);
        }
    }

    private String getNewDirectoryPath(final String absolutePath, final String newDirName) {
        int startIndex = 0;
        for (int i = absolutePath.length() - 2; i >= 0; i--) {
            if (absolutePath.charAt(i) == '/') {
                startIndex = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder(absolutePath.substring(0, startIndex + 1));
        sb.append(newDirName + "/");
        return sb.toString();
    }

    @RequiredArgsConstructor
    static class UpdatingDirectory {
        private final Directory directory;
        private final String newDirPath;

        private void update() {
            this.directory.setPath(newDirPath);
        }
    }

}
