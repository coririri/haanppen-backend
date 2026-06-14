package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.online.dto.OnlineCategoryInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineCategoryRepository extends JpaRepository<OnlineCategory, Long> {

    @Query("SELECT new com.hanpyeon.academyapi.online.dto.OnlineCategoryInfo(oc.id, oc.categoryName, oc.creationTime) FROM OnlineCategory oc WHERE oc.parentCategory.id = :categoryId")
    List<OnlineCategoryInfo> queryChildCategories(@Param("categoryId") final Long categoryId);

    @Query("SELECT new com.hanpyeon.academyapi.online.dto.OnlineCategoryInfo(oc.id, oc.categoryName, oc.creationTime) FROM OnlineCategory oc WHERE oc.parentCategory IS null")
    List<OnlineCategoryInfo> queryRootCategories();

    @Query("SELECT oc FROM OnlineCategory oc LEFT JOIN FETCH oc.childCategories WHERE oc.id = :categoryId")
    Optional<OnlineCategory> findDeleteTargetDirectory(@Param("categoryId") final Long categoryId);
}
