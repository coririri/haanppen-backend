package com.hanpyeon.academyapi.dir.service.form.resolver;

import com.hanpyeon.academyapi.dir.service.form.validate.DirectoryPathFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BasicDirectoryPathFormResolver implements DirectoryPathFormResolver {
    private final DirectoryPathFormValidator directoryPathFormValidator;

    @Override
    public String resolveToAbsolutePath(String dirPath, String dirName) {
        final String resolvedDirPath = getDirPath(dirPath) + dirName + "/";
        directoryPathFormValidator.validateDirPath(resolvedDirPath);
        return resolvedDirPath;
    }

    @Override
    public String resolveToAbsolutePath(String dirPath) {
        final String resolvedDirPath = getDirPath(dirPath);
        directoryPathFormValidator.validateDirPath(resolvedDirPath);
        return resolvedDirPath;
    }

    private String getDirPath(final String dirPath) {
        if (dirPath.charAt(dirPath.length() - 1) != '/') {
            return dirPath + "/";
        }
        return dirPath;
    }
}
