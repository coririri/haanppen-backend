package com.hanpyeon.academyapi.paging;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}
