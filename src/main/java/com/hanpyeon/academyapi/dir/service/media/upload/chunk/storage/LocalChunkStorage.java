package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.storage.LocalStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
class LocalChunkStorage extends LocalStorage implements ChunkStorage {

    public LocalChunkStorage(@Value(value = "${server.local.chunk.storage}") String storagePath) {
        super(storagePath);
    }

    @Override
    public void save(ChunkedFile chunkedFile) {
        super.store(chunkedFile);
    }

    @Override
    public ChunkGroup loadSequentialRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo) {
        return new ChunkGroup(chunkGroupInfo, loadSequentialFiles(chunkGroupInfo), getTotalSize(chunkGroupInfo));
    }

    private Stream<Path> loadPaths(final ChunkGroupInfo chunkGroupInfo) {
        final String groupId = chunkGroupInfo.getGroupId();
        try {
            return Files.walk(Paths.get(this.storagePath))
                    .filter(Files::isRegularFile)
                    .filter(path -> String.valueOf(path.getFileName()).startsWith(groupId));
        } catch (IOException e) {
            throw new ChunkException("청크파일 여는 중 예외 발생", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    private Long getTotalSize(final ChunkGroupInfo chunkGroupInfo) {
        return loadPaths(chunkGroupInfo)
                .mapToLong(path -> {
                    try {
                        return Files.size(path);
                    } catch (IOException e) {
                        throw new ChunkException("다운로드 된 청크들의 사이즈를 확인할 수 없습니다", ErrorCode.CHUNK_SIZE_EXCEPTION);
                    }
                }).sum();
    }

    private Integer getSequence(final Path path, final ChunkGroupInfo chunkGroupInfo) {
        final String sequenceValue = String.valueOf(path.getFileName()).replace(chunkGroupInfo.getGroupId(), "").replace("_", "");
        return Integer.parseInt(sequenceValue);
    }

    private List<InputStream> loadSequentialFiles(final ChunkGroupInfo chunkGroupInfo) {
        return loadPaths(chunkGroupInfo)
                .sorted((path1, path2) -> {
                    final int path1Sequence = getSequence(path1, chunkGroupInfo);
                    final int path2Sequence = getSequence(path2, chunkGroupInfo);
                    return path1Sequence - path2Sequence;
//                    return String.valueOf(path2.getFileName()).compareTo(String.valueOf(path1.getFileName()))
                })
                .map(path -> {
                    try {
                        return Files.newInputStream(path, StandardOpenOption.READ);
                    } catch (IOException e) {
                        throw new ChunkException("청크 접근중 에러 발생" + e.getMessage(), ErrorCode.CHUNK_ACCESS_EXCEPTION);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * @param chunkGroupInfo ChunkStorage의 delete 오버헤드를 클라이언트가 감내해야할 이유 X
     *                       1. Async를 통한 비동기 로직 처리로 스레드를 더 소요 But 응답시간 단축
     *                       2. 스케쥴러를 통한 chunkFile 배치 삭제
     */
    @Override
    public void removeChunks(ChunkGroupInfo chunkGroupInfo) {
        log.debug("템프 파일 지우기 시작");
        loadPaths(chunkGroupInfo)
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                        log.debug(path + "지워지기 성공!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        log.debug("템프 파일 지우기 종료");
    }
}
