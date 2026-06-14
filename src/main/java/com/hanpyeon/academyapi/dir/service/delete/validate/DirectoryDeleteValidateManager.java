package com.hanpyeon.academyapi.dir.service.delete.validate;

import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryDeleteValidateManager {
    final List<DeleteDirectoryValidator> deleteDirectoryValidators;

    @Transactional(propagation = Propagation.MANDATORY)
    public void validate(final DirectoryDeleteCommand directoryDeleteCommand) {
        deleteDirectoryValidators.stream()
                .forEach(deleteDirectoryValidator -> deleteDirectoryValidator.validate(directoryDeleteCommand));
    }
}
