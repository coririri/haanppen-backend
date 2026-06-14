package com.hanpyeon.academyapi.dir.dao;

import com.hanpyeon.academyapi.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    Optional<Directory> findDirectoryByPath(final String path);
    @Query("SELECT e FROM Directory e WHERE e.path LIKE concat(:dirPath, '%', '/') AND e.path NOT LIKE concat(:dirPath, '%', '/', '%', '/')")
    List<Directory> queryDirectoriesOneDepth(@Param("dirPath") final String dirPath);
    @Query("SELECT e FROM Directory e WHERE e.path LIKE concat(:dirPath, '%')")
    List<Directory> queryChildDirectories(@Param("dirPath") final String dirPath);
    Boolean existsAllByPathIn(List<String> paths);
    List<Directory> findDirectoriesByMediasContaining(final Media media);
}
