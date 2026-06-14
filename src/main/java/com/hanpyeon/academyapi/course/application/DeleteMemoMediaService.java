package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.DeleteMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.media.validate.MemoMediaContainerValidateManager;
import com.hanpyeon.academyapi.course.application.port.in.DeleteMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.out.DeleteSingleMemoMediaPort;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaContainerPort;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMemoMediaService implements DeleteMemoMediaUseCase {

    private final MemoMediaContainerCreator containerCreator;
    private final MemoMediaContainerValidateManager containerValidateManager;
    private final UpdateMemoMediaContainerPort updateMemoMediaContainerPort;
    private final DeleteSingleMemoMediaPort deleteSingleMemoMediaPort;

    @Override
    @Transactional
    public void delete(DeleteMemoMediaCommand deleteMemoMediaCommand) {
        final MemoMediaContainer container = containerCreator.createContainer(deleteMemoMediaCommand.memoId());
        container.deleteMemoMedia(deleteMemoMediaCommand.memoMediaId());
        containerValidateManager.validate(container);
        deleteSingleMemoMediaPort.delete(deleteMemoMediaCommand.memoMediaId());
        updateMemoMediaContainerPort.save(container);
    }
}
