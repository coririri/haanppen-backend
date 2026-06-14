package com.hanpyeon.academyapi.dir.service.delete.validate;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DirectoryChildValidator implements DeleteDirectoryValidator {
    @Override
    public void validate(DirectoryDeleteCommand directoryDeleteCommand) {
        final List<Directory> directories = directoryDeleteCommand.getDirectories();
        if (directories.size() <= 1) {
            return;
        }
        if (directoryDeleteCommand.getDeleteChildes()) {
            return;
        }
        throw new DirectoryException("자식 디렉토리가 있으며, 자식을 지우기 위한 설정 필요", ErrorCode.DIRECTORY_CANNOT_DELETE);
    }
}
