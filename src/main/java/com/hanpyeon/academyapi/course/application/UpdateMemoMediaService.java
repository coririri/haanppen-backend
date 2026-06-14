package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.media.validate.MemoMediaContainerValidateManager;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaContainerPort;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateMemoMediaService implements UpdateMemoMediaUseCase {
    private final MemoMediaContainerCreator containerCreator;
    private final MemoMediaContainerValidateManager containerValidateManager;
    private final UpdateMemoMediaContainerPort updateMemoMediaContainerPort;

    @Override
    @Transactional
    public void updateMediaMemo(UpdateMediaMemoCommand updateMediaMemoCommand) {
        final MemoMediaContainer container = containerCreator.createContainer(updateMediaMemoCommand);

        log.debug(updateMediaMemoCommand.toString());
        updateMediaMemoCommand.mediaSequences().stream()
                        .forEach(mediaSequence -> container.updateMemoMediaSequence(mediaSequence));
        containerValidateManager.validate(container);
        updateMemoMediaContainerPort.save(container);
    }
}
