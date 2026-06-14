package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.dao.Directory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DirectoryDepthDescendingSortResolverTest {

    private DescendingSoryByDepthResolver directoryDepthDescendingSortResolver = new DescendingSoryByDepthResolver();

    @Test
    void testDescending() {
        Directory testDir1 = new Directory(null, "/test/test/test/test/", null, null);
        Directory testDir2 = new Directory(null, "/test/test/", null, null);
        Directory testDir3 = new Directory(null, "/test/test/helloworldMynameisHeejongYoon/", null, null);
        Directory testDir4 = new Directory(null, "/test/test/test/test/반갑다내이름유니종/", null, null);
        final List<Directory> directories = List.of(
                testDir1,
                testDir2,
                testDir3,
                testDir4
                );

        final List<Directory> sortedDirectories = directoryDepthDescendingSortResolver.getDecsendingList(directories);
        Assertions.assertThatList(sortedDirectories).containsExactlyElementsOf(List.of(
                testDir4,
                testDir1,
                testDir3,
                testDir2
        ));
    }

}