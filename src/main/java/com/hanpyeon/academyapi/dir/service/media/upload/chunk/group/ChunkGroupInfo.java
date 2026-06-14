package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

public interface ChunkGroupInfo {

    void init();

    String getGroupId();

    String getChunkUniqueId();

    boolean clear();

    String getDirPath();

    String getFileName();

    String getExtension();

    boolean chunkIndexFulfilled();

    void isMatchToCurrIndex(final Long lastChunkIndex);

    void updateGroupIndex(final Long lastChunkSize);

    Long getRequiringChunkSize();

    Long getNextChunkIndex();

    Long getMediaDuration();

    Long getTotalSize();
}
