package com.hanpyeon.academyapi.online.service.category;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCategory;
import com.hanpyeon.academyapi.online.dao.OnlineCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnlineCategoryDeleteService {
    private final OnlineCategoryRepository onlineCategoryRepository;

    @Transactional
    public void deleteChildCategories(final Long onlineCategoryId) {
        final OnlineCategory targetCategory = loadTargetCategory(onlineCategoryId);
        onlineCategoryRepository.delete(targetCategory);
    }

    private OnlineCategory loadTargetCategory(final Long onlineCategoryId) {
        return onlineCategoryRepository.findDeleteTargetDirectory(onlineCategoryId)
                .orElseThrow(() -> new BusinessException( onlineCategoryId + " 카테고리를찾을 수 없음 : " + onlineCategoryId,
                        ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
