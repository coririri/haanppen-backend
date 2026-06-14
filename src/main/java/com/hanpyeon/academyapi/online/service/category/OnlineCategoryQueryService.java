package com.hanpyeon.academyapi.online.service.category;

import com.hanpyeon.academyapi.online.dao.OnlineCategoryRepository;
import com.hanpyeon.academyapi.online.dto.OnlineCategoryInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineCategoryQueryService {
    private final OnlineCategoryRepository onlineCategoryRepository;

    public List<OnlineCategoryInfo> queryChildCategories(final Long categoryId) {
        return onlineCategoryRepository.queryChildCategories(categoryId);
    }

    public List<OnlineCategoryInfo> queryRootCategories() {
        return onlineCategoryRepository.queryRootCategories();
    }
}
