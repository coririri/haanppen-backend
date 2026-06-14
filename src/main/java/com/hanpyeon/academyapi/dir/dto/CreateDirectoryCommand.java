package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.account.entity.Member;

public record CreateDirectoryCommand(
        String dirPath,
        String newDirName,
        String newDirAbsolutePath,
        Boolean canViewByEveryone,
        Boolean canModifyByEveryone,
        Member requestMember) {
}
