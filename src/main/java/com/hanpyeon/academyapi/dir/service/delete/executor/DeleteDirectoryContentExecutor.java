package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.dao.Directory;

import java.util.Collection;

interface DeleteDirectoryContentExecutor {

    void delete(final Collection<Directory> directories);
}
