package com.ll.topcastingbe.domain.review.service;


import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.UserNotFoundException;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.review.dto.AddNormalReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ModifyReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ReviewDetailResponseDto;
import com.ll.topcastingbe.domain.review.dto.ReviewListResponseDto;
import com.ll.topcastingbe.domain.review.entity.Review;
import com.ll.topcastingbe.domain.review.exception.DuplicateReviewException;
import com.ll.topcastingbe.domain.review.exception.ReviewNotFoundException;
import com.ll.topcastingbe.domain.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;


    public ReviewListResponseDto findReviewList() {

        List<Review> all = reviewRepository.findAll();
        return new ReviewListResponseDto(all);
    }


    public ReviewDetailResponseDto findReviewDetail(Long reviewId) {
        Optional<Review> or = reviewRepository.findById(reviewId);
        if (or.isEmpty()) {
            throw new ReviewNotFoundException();
        }

        return new ReviewDetailResponseDto(or.get());
    }


    //Item 이름으로 리뷰 추가
    @Transactional
    public ReviewDetailResponseDto addNormalReview(String itemName, Member member, UUID orderId,
                                                   AddNormalReviewRequestDto addNormalReviewRequestDto) {

        Orders orders = orderRepository.findById(orderId)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        List<OrderItem> findOrderItems = orderItemRepository.findByOrder(orders);
        OrderItem findOrderItem = findOrderItems.stream()
                                          .filter(orderItem -> orderItem.getItemName().equals(itemName))
                                          .findFirst()
                                          .orElseThrow(
                                                  () -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        
        Review review = Review.builder()
                                .writer(member)
                                .orderItem(findOrderItem)
                                .image(null)
                                .title(addNormalReviewRequestDto.getTitle())
                                .content(addNormalReviewRequestDto.getContent())
                                .rating(addNormalReviewRequestDto.getRating())
                                .build();

        return new ReviewDetailResponseDto((Review) reviewRepository.save(review));
    }

    @Transactional
    public ReviewDetailResponseDto modifyReview(Long reviewId, ModifyReviewRequestDto modifyReviewRequestDto) {
        Review findReview = reviewRepository.findById(reviewId)
                                    .orElseThrow(() -> new ReviewNotFoundException());

        if (modifyReviewRequestDto.getImgUrl() != null) {
            //TODO 이미지 url 존재할시
        }
        findReview.modifyTitle(modifyReviewRequestDto.getTitle());
        findReview.modifyContent(modifyReviewRequestDto.getContent());
        findReview.modifyImg(null);
        findReview.modifyRating(modifyReviewRequestDto.getRating());
        return new ReviewDetailResponseDto(findReview);
    }


    @Transactional
    public void deleteReview(Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId)
                                    .orElseThrow(() -> new ReviewNotFoundException());
        reviewRepository.delete(findReview);
    }

    public ReviewListResponseDto findReviewRating(int rating, Long itemId) {
        List<Review> findReviewList = reviewRepository.findByRatingAndItemId(rating, itemId);
        return new ReviewListResponseDto(findReviewList);
    }

    public ReviewListResponseDto findItemReviewList(Long itemId, String sort) {
        if (sort.equals("DESC")) {
            return new ReviewListResponseDto(reviewRepository.findByItemIdOrderByRatingDesc(itemId));
        } else if (sort.equals("ASC")) {
            return new ReviewListResponseDto(reviewRepository.findByItemIdOrderByRatingAsc(itemId));
        } else {
            throw new ReviewNotFoundException();
        }
    }

    public ReviewListResponseDto findMemberReviewList(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                                    .orElseThrow(() -> new UserNotFoundException());
        return new ReviewListResponseDto(reviewRepository.findByWriter(findMember));
    }

    public void makeReviewSummary() {

    }

    public void verifyReview(String itemName, UUID orderId) {

        Orders findOrders = orderRepository.findById(orderId)
                                    .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        List<OrderItem> findOrderItems = orderItemRepository.findByOrder(findOrders);
        OrderItem findOrderItem = findOrderItems.stream()
                                          .filter(orderItem -> orderItem.getItemName().equals(itemName))
                                          .findFirst()
                                          .orElseThrow(
                                                  () -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        Long reviewCount = reviewRepository.countReviewsByOrderItem(findOrderItem);

        if (reviewCount >= 1) {
            throw new DuplicateReviewException();
        }
    }
}
