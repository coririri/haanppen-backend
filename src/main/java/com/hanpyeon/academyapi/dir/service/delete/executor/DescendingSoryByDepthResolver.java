package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.dao.Directory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class DescendingSoryByDepthResolver {
    public List<Directory> getDecsendingList(final List<Directory> directories) {
        return directories.stream()
                .map(directory -> new DirectoryDepth(directory, this.getDepth(directory.getPath())))
                .sorted((directory1, directory2) -> directory2.depth() - directory1.depth())
                .map(directoryDepth -> directoryDepth.directory)
                .collect(Collectors.toUnmodifiableList());
    }

    private Integer getDepth(final String path) {
        int depthCount = 0;
        for (int i = 0; i < path.length() - 1; i++) {
            if (path.charAt(i) == '/') {
                depthCount++;
            }
        }
        return depthCount;
    }

    private record DirectoryDepth(
            Directory directory,
            Integer depth) {
    }
}
