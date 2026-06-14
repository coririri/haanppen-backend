package com.hanpyeon.academyapi.banner.controller;

import com.hanpyeon.academyapi.banner.BannerService;
import com.hanpyeon.academyapi.banner.dto.AddBannerCommand;
import com.hanpyeon.academyapi.banner.dto.BannerResponse;
import com.hanpyeon.academyapi.banner.dto.ChangeBannerCommand;
import com.hanpyeon.academyapi.banner.dto.DeleteBannerCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BannerController {
    private final BannerService bannerService;

    @GetMapping("/api/banners")
    public ResponseEntity<List<BannerResponse>> queryAllBanners() {
        return ResponseEntity.ok(bannerService.queryAllBanners());
    }

    @PostMapping("/api/banners")
    public ResponseEntity<?> addBanner(
            @RequestBody AddBannerRequest addBannerRequest
    ) {
        final AddBannerCommand addBannerCommand = new AddBannerCommand(addBannerRequest.content);
        bannerService.addBanner(addBannerCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/banners")
    public ResponseEntity<?> modifyBanner(
            @RequestBody ChangeBannerRequest changeBannerRequest
    ) {
        final ChangeBannerCommand changeBannerCommand = new ChangeBannerCommand(
                changeBannerRequest.bannerId(),
                changeBannerRequest.content()
        );
        bannerService.changeBanner(changeBannerCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/banners/{bannerId}")
    public ResponseEntity<?> deleteBanner(
            @PathVariable(required = true) final Long bannerId
    ) {
        final DeleteBannerCommand deleteBannerCommand = new DeleteBannerCommand(bannerId);
        bannerService.deleteBanner(deleteBannerCommand);
        return ResponseEntity.noContent().build();
    }

    record AddBannerRequest(
            String content
    ) {
    }
    record ChangeBannerRequest(
            Long bannerId,
            String content
    ) {
    }
}
