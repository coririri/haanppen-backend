package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DeleteByTeacherHandlerTest {

    @Mock
    DeleteDirectoryContentExecutor deleteDirectoryContentExecutor;
    DescendingSoryByDepthResolver directoryDepthResolver = new DescendingSoryByDepthResolver();
    DeleteByTeacherHandler deleteByTeacherHandler;

    @BeforeEach
    void init() {
        this.deleteByTeacherHandler = new DeleteByTeacherHandler(directoryDepthResolver, deleteDirectoryContentExecutor);
    }

    @ParameterizedTest
    @MethodSource(value = "provideOwnerTestSource")
    void directoriesSelectingTest(List<Directory> directories, final Member requestMember, final Boolean deleteChildes, final Integer expectedDeletedDirectoryCount) {
        // given
        final DirectoryDeleteCommand directoryDeleteCommand = new DirectoryDeleteCommand(directories, requestMember, deleteChildes);
        Assertions.assertEquals(deleteByTeacherHandler.process(directoryDeleteCommand), expectedDeletedDirectoryCount);
    }

    static Stream<Arguments> provideOwnerTestSource() {
        final Member requestMember = Member.builder().build();
        return Stream.of(
                Arguments.of(
                        List.of(new Directory(requestMember, "/test/test/", true, true),
                                new Directory(Member.builder().build(), "/test/test/hello/", true, true),
                                new Directory(requestMember, "/test/test/test/me/", true, true),
                                new Directory(requestMember, "/test/test/test/test/", true, true)
                        ),
                        requestMember,
                        true,
                        2
                ),
                Arguments.of(
                        List.of(
                                new Directory(requestMember, "/test/test/", true, true),
                                new Directory(requestMember, "/test/test/test/", true, true),
                                new Directory(requestMember, "/test/test/test/me/", true, true),
                                new Directory(Member.builder().build(), "/test/test/test/test/", true, true)
                        ),
                        requestMember,
                        true,
                        1
                ),
                Arguments.of(
                        List.of(
                                new Directory(requestMember, "/test/test/", true, true),
                                new Directory(requestMember, "/test/test/hello/", true, true),
                                new Directory(requestMember, "/test/test/hello/me/", true, true),
                                new Directory(Member.builder().build(), "/test/test/test/test/", true, true)
                        ),
                        requestMember,
                        true,
                        2
                ),
                Arguments.of(
                        List.of(
                                new Directory(requestMember, "/test/test/", true, true),
                                new Directory(requestMember, "/test/test/ex1/", true, true),
                                new Directory(requestMember, "/test/test/ex2/", true, true),
                                new Directory(Member.builder().build(), "/test/test/ex3/", true, true),
                                new Directory(requestMember, "/test/test/ex3/test", true, true),
                                new Directory(requestMember, "/test/test/ex3/test/forme", true, true)
                        ),
                        requestMember,
                        true,
                        4
                )
        );

    }

}