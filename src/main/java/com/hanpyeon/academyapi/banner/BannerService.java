package com.hanpyeon.academyapi.banner;

import com.hanpyeon.academyapi.banner.dto.AddBannerCommand;
import com.hanpyeon.academyapi.banner.dto.BannerResponse;
import com.hanpyeon.academyapi.banner.dto.ChangeBannerCommand;
import com.hanpyeon.academyapi.banner.dto.DeleteBannerCommand;
import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> queryAllBanners() {
        return bannerRepository.queryAllBanners();
    }

    @Transactional
    public void addBanner(final AddBannerCommand addBannerCommand) {
        final Banner banner = new Banner(addBannerCommand.content());
        bannerRepository.save(banner);
    }

    @Transactional
    public void changeBanner(@Validated final ChangeBannerCommand changeBannerCommand) {
        final Banner banner = bannerRepository.findById(changeBannerCommand.bannerId())
                .orElseThrow(() -> new BusinessException("찾을 수 없는 배너", ErrorCode.BANNER_EXCEPTION));
        banner.changeContent(changeBannerCommand.content());
    }

    @Transactional
    public void deleteBanner(@Validated final DeleteBannerCommand deleteBannerCommand) {
        bannerRepository.deleteById(deleteBannerCommand.bannerId());
    }
}
