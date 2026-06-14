package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class DeleteByTeacherHandler implements DeleteDirectoryHandler {
    private final DescendingSoryByDepthResolver descendingSoryByDepthResolver;
    private final DeleteDirectoryContentExecutor deleteDirectoryContentManager;

    @Override
    public Integer process(DirectoryDeleteCommand directoryDeleteCommand) {
        final Collection<Directory> deletableDirectories = getRemovableDirectories(directoryDeleteCommand);
        deleteDirectoryContentManager.delete(deletableDirectories);
        return deletableDirectories.size();
    }

    private Collection<Directory> getRemovableDirectories(DirectoryDeleteCommand directoryDeleteCommand) {
        final List<Directory> descendingSortedDirectories = descendingSoryByDepthResolver.getDecsendingList(directoryDeleteCommand.getDirectories());
        final Set<Directory> unDeletableDirectories = getUnDeletableDirectories(descendingSortedDirectories, directoryDeleteCommand.getRequestMember());

        return descendingSortedDirectories.stream().filter(directory -> !unDeletableDirectories.contains(directory))
                .collect(Collectors.toList());
    }

    private Set<Directory> getUnDeletableDirectories(final List<Directory> descendingSortedDirectories, final Member requestMember) {
        final Set<Directory> unDeletableDirectories = new HashSet<>();
        for (final Directory directory : descendingSortedDirectories) {
            if (!isOwner(directory.getOwner(), requestMember)) {
                unDeletableDirectories.add(directory);
                log.info(directory.toString());
                continue;
            }
            if (doesChildRemain(unDeletableDirectories, directory.getPath())) {
                unDeletableDirectories.add(directory);
                log.info(directory.toString());
            }
        }
        return unDeletableDirectories;
    }

    private boolean isOwner(final Member ownerMember, final Member requestMember) {
        return ownerMember.equals(requestMember);
    }

    private boolean doesChildRemain(final Set<Directory> undeletedDirectories, final String currPath) {
        return undeletedDirectories.stream()
                .anyMatch(directory -> directory.getPath().startsWith(currPath));
    }

    @Override
    public boolean applicable(Role role) {
        if (role.equals(Role.TEACHER)) {
            return true;
        }
        return false;
    }
}
