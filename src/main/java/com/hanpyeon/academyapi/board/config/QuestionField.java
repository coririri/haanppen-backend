package com.hanpyeon.academyapi.board.config;

import java.util.Arrays;

enum QuestionField {
    DATE("date", "registeredDateTime"),
    SOLVED("solve", "solved");

    private final String requestFieldName;
    private final String entityFieldName;

    QuestionField(String requestFieldName, String entityFieldName) {
        this.requestFieldName = requestFieldName;
        this.entityFieldName = entityFieldName;
    }

    static String mapToEntityFieldName(final String requestFieldName) {
        return Arrays.stream(values())
                .filter(params -> params.requestFieldName.equals(requestFieldName))
                .findAny()
                .map(parameter -> parameter.entityFieldName)
                .orElseThrow(IllegalArgumentException::new);
    }
}
