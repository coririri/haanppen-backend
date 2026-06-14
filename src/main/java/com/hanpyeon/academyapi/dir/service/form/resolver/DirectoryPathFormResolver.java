package com.hanpyeon.academyapi.dir.service.form.resolver;

public interface DirectoryPathFormResolver {
    String resolveToAbsolutePath(final String dirPath, final String dirName);
    String resolveToAbsolutePath(final String dirPath);
}
