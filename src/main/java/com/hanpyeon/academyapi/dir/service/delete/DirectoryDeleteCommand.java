package com.hanpyeon.academyapi.dir.service.delete;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.dir.dao.Directory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class DirectoryDeleteCommand {
    private final List<Directory> directories;
    private final Member requestMember;
    private final Boolean deleteChildes;
}
