package com.ll.topcastingbe.domain.option.service;

import com.ll.topcastingbe.domain.option.dto.request.OptionModifyRequestDto;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.exception.OptionNotFoundException;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public void optionModify(Long optionId, OptionModifyRequestDto modifyDto) {
        //해당 옵션이 존재하는지 검증
        Option option = optionRepository.findById(optionId).orElseThrow(OptionNotFoundException::new);

        //옵션 이름과 수량 수정
        option.change(modifyDto.getColorName(),modifyDto.getStock());
    }

}
