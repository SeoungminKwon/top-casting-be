package com.ll.topcastingbe.domain.item.service;

import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.category.exception.CategoryErrorMessage;
import com.ll.topcastingbe.domain.category.exception.CategoryNotExistException;
import com.ll.topcastingbe.domain.category.repository.SubCategoryRepository;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;
import com.ll.topcastingbe.domain.image.service.ImageService;
import com.ll.topcastingbe.domain.item.dto.request.ItemCreateRequestDto;
import com.ll.topcastingbe.domain.item.dto.response.ItemDetailResponseDto;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.exception.ItemNotExistException;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.option.dto.ItemDetailOptionResponseDto;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ImageService imageService;

    public ItemDetailResponseDto findItem(Long itemId) {
        Item item = itemRepository.findByItemIdWithImageAndOption(itemId)
                .orElseThrow(() -> new ItemNotExistException());

        List<ItemDetailOptionResponseDto> optionDtos = optionRepository.findByItemId(item.getId())
                .stream()
                .map(ItemDetailOptionResponseDto::toDto)
                .toList();

        return ItemDetailResponseDto.toDto(item, optionDtos);

    }

    @Transactional
    public Long saveItem(ItemCreateRequestDto itemRequestDto) {

        //요청된 카테고리가 있는지 검증
        SubCategory subCategory = subCategoryRepository.findByMainCategoryIdAndSubcategoryId(
                        itemRequestDto.getMainCategoryId(), itemRequestDto.getSubCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(CategoryErrorMessage.CATEGORY_NOT_EXIST));

        Image image = imageService.uploadImage(itemRequestDto.getItemName(), itemRequestDto.getItemImage());
        DetailedImage detailedImage = imageService.uploadDetailedImage(itemRequestDto.getItemName(),
                itemRequestDto.getItemDetailedImage());

        //아이템 엔티티 생성
        Item item = Item.builder()
                .itemName(itemRequestDto.getItemName())
                .itemPrice(itemRequestDto.getItemPrice())
                .image(image)
                .detailedImage(detailedImage)
                .mainCategory(subCategory.getParentCategory())
                .subCategory(subCategory)
                .build();
        Item createdItem = itemRepository.save(item);

        //옵션 엔티티 생성
        itemRequestDto.getItemColors()
                .forEach(o -> optionRepository.save(Option.builder()
                        .colorName(o.getColorName())
                        .stock(o.getStock())
                        .item(item)
                        .build())
                );

        return createdItem.getId();
    }
}
