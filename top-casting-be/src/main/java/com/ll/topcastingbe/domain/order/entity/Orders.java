package com.ll.topcastingbe.domain.order.entity;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.exception.AuthException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String customerName;

    private String customerPhoneNumber;

    private String customerAddress;

    private Long totalItemQuantity;

    private Long totalItemPrice;

    public void checkAuthorizedMember(final Member member) {
        if (!Objects.equals(this.member.getId(), member.getId())) {
            throw new AuthException(ErrorMessage.UNAUTHORIZED_USER);
        }
    }

    public void modifyOrder(final ModifyOrderRequest modifyOrderRequest) {
        this.orderStatus = OrderStatus.checkOrderStatus(modifyOrderRequest.orderStatus());
    }

    public void modifyOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
