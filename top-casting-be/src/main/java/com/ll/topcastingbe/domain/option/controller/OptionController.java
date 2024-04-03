package com.ll.topcastingbe.domain.option.controller;

import com.ll.topcastingbe.domain.option.dto.request.OptionModifyRequestDto;
import com.ll.topcastingbe.domain.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/options")
@Slf4j
public class OptionController {

    private final OptionService optionService;

    @PreAuthorize("hasRole('ADMIN')") //옵션 수정은 관리자만 가능
    @PatchMapping("/{optionId}")
    public ResponseEntity<?> modifyOption(@PathVariable Long optionId,
                                          @RequestBody OptionModifyRequestDto modifyDto) {
        optionService.optionModify(optionId, modifyDto);
        return ResponseEntity.ok(null);
    }
}
