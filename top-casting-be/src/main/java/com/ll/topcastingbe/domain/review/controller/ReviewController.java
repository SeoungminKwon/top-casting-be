package com.ll.topcastingbe.domain.review.controller;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.UserAndWriterNotMatchException;
import com.ll.topcastingbe.domain.member.service.MemberService;
import com.ll.topcastingbe.domain.review.dto.AddNormalReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ModifyReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ReviewDetailResponseDto;
import com.ll.topcastingbe.domain.review.dto.ReviewListResponseDto;
import com.ll.topcastingbe.domain.review.service.ReviewService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    //전체 리뷰
    @GetMapping("/review")
    public ResponseEntity<ReviewListResponseDto> reviewList() {
        return ResponseEntity.ok().body(reviewService.findReviewList());
    }

    //리뷰 ID로 단건조회
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> reviewDetail(@PathVariable Long reviewId) {
        return ResponseEntity.ok().body(reviewService.findReviewDetail(reviewId));
    }

    //해당 ItemId에 해당하는 reviewList 조회
    @GetMapping("/items/{itemId}/review")
    public ResponseEntity<ReviewListResponseDto> itemReviewList(@PathVariable Long itemId,
                                                                @RequestParam(defaultValue = "DESC") String sort) {

        return ResponseEntity.ok().body(reviewService.findItemReviewList(itemId, sort));
    }


    //리뷰 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("orders/{orderId}/items/{itemName}/review")
    public ResponseEntity<ReviewDetailResponseDto> reviewAdd(@PathVariable String itemName,
                                                             @PathVariable UUID orderId,
                                                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                             @RequestBody AddNormalReviewRequestDto addNormalReviewRequestDto) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok()
                       .body(reviewService.addNormalReview(itemName, member, orderId,
                               addNormalReviewRequestDto));
    }

    //리뷰 수정
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> reviewModify(@PathVariable Long reviewId, Principal principal,
                                                                @RequestBody ModifyReviewRequestDto modifyReviewRequestDto) {
        if (reviewService.findReviewDetail(reviewId).getWriter() != principal.getName()) {
            throw new UserAndWriterNotMatchException();
        }
        return ResponseEntity.ok()
                       .body(reviewService.modifyReview(reviewId, modifyReviewRequestDto));
    }

    //리뷰 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/review/{reviewId}")
    public void reviewDelete(@PathVariable Long reviewId, Principal principal) {
        if (reviewService.findReviewDetail(reviewId).getWriter() != principal.getName()) {
            throw new UserAndWriterNotMatchException();
        }
        reviewService.deleteReview(reviewId);
    }

    //    특정 평점별 리뷰
    @GetMapping("items/{itemId}/review/rating/{rating}")
    public ResponseEntity<ReviewListResponseDto> ItemReviewRatingList(@PathVariable int rating,
                                                                      @PathVariable Long itemId) {
        return ResponseEntity.ok().body(reviewService.findReviewRating(rating, itemId));
    }

    //    member가 작성한 리뷰
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/members/{memberId}/review")
    public ResponseEntity<ReviewListResponseDto> memberReviewList(@PathVariable Long memberId, Principal principal) {
        if (memberService.findMember(principal.getName()).getId() != memberId) {
            throw new UserAndWriterNotMatchException("권한이 없습니다.");
        }
        return ResponseEntity.ok().body(reviewService.findMemberReviewList(memberId));
    }

    //    제미나이
    @GetMapping("/items/{itemId}/review/summary")
    public ResponseEntity ItemReviewSummary(@PathVariable Long itemId) {
        //TODO 추후 제미나이 요약 기능 추가 예정
        reviewService.makeReviewSummary();
        return null;
    }

    @GetMapping("/orders/{orderId}/items/{itemName}/review")
    public ResponseEntity reviewVerify(@PathVariable String itemName, @PathVariable String orderId) {
        UUID uuidOrderId = UUID.fromString(orderId);
        reviewService.verifyReview(itemName, uuidOrderId);
        return ResponseEntity.ok().body("리뷰 검증 완료");
    }


}
