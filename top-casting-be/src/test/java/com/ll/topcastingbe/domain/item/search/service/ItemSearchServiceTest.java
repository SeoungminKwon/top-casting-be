package com.ll.topcastingbe.domain.item.search.service;

import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@SpringBootTest
class ItemSearchServiceTest {

    @Autowired
    private ItemSearchService itemSearchService;

    @Test
    public void getItemByMainCategoryTest() {

        Long mainCategoryId = 2L; // 메인 카테고리 ID 설정
        Pageable pageable = PageRequest.of(0, 10); // 페이지 및 크기 설정

        Slice<SearchItemDto> content = itemSearchService.getItemsByMainCategory(pageable, mainCategoryId);
        System.out.println(content.getContent());
    }

    @Test
    public void getItemBySubCategory() {
        Long subCategoryId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Slice<SearchItemDto> content = itemSearchService.getItemsBySubcategory(pageable, subCategoryId);
        System.out.println(content.getContent());
    }
}
