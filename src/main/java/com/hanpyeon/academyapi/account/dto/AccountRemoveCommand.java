package com.hanpyeon.academyapi.account.dto;

import java.util.List;

public record AccountRemoveCommand(
        List<Long> targetIds
) {
}
